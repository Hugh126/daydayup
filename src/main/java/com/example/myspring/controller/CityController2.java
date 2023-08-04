package com.example.myspring.controller;

import com.example.myspring.common.Result;
import com.example.myspring.entity.ErpCity;
import com.example.myspring.service.BatchRunner;
import com.example.myspring.service.ErpCityService;
import com.example.myspring.service.TransactionHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * 测试
 * 1、手动事务提交
 * 2、多线程事务提交回滚
 */
@Slf4j
@RestController
@RequestMapping("/city2")
public class CityController2 extends TransactionHandle<ErpCity> {


    @Autowired
    private ErpCityService erpCityService;


    /**
     * 手动事务
     * 设置name唯一触发 重复异常 来触发回滚
     * @param recordNum
     * @return
     */
    @RequestMapping("/batchInsert1")
    public Result<?> batchInsert1(@RequestParam Integer recordNum) {
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
        try{
            IntStream.range(0, recordNum).mapToObj(t -> {
                int n = new Random().nextInt(recordNum);
                return build(n);
            }).forEach(x -> erpCityService.save(x));
            log.warn("--success--");
            transactionManager.commit(transStatus);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("--error--");
            transactionManager.rollback(transStatus);
        }
        return Result.ok("done");
    }

    private void saveInThread(ErpCity city) {
        String tName = Thread.currentThread().getName();
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
        try {
            erpCityService.save(city);
            transactionManager.commit(transStatus);
            log.warn("线程{}保存成功,已提交", tName);
        }catch (Exception e){
            transactionManager.rollback(transStatus);
            log.warn("线程{}保存失败{},已回滚", tName, e.getMessage());
        }
    }

    private boolean saveInThread3(ErpCity city, List<TransactionStatus> statusList) {
        String tName = Thread.currentThread().getName();
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
        try {
            boolean save = erpCityService.save(city);
            log.warn("线程{}保存{}成功", tName, city.getName());
            return save;
        }catch (Exception e){
            log.warn("线程{}保存{}失败{}", new Object[]{tName, city.getName(), e.getMessage()});
            return false;
        }finally {
            statusList.add(transStatus);
        }
    }


    /**
     * 改成多线程 分别手动处理事务
     * 线程间独立提交，互不影响，类似 使用transactionTemplate
     * @param recordNum
     * @return
     */
    @RequestMapping("/batchInsert2")
    public Result<?> batchInsert2(@RequestParam Integer recordNum) {
        IntStream.range(0, recordNum).mapToObj(t -> {
            int n = new Random().nextInt(recordNum);
            return build(n);
        }).forEach(city -> {
            new Thread(() -> saveInThread(city)).start();
        });
        return Result.ok("done");
    }


    /**
     * 业务放到线程中，并手动事务管理
     * @param recordNum
     * @return
     */
    @RequestMapping("/batchInsert3")
    public Result<?> batchInsert3(@RequestParam Integer recordNum) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 需要1个东西保证所有任务都执行完毕
        CountDownLatch downLatch = new CountDownLatch(recordNum);
        CountDownLatch start = new CountDownLatch(recordNum);
        AtomicBoolean flag = new AtomicBoolean(true);

        IntStream.range(0, recordNum).mapToObj(t -> {
            int n = new Random().nextInt(recordNum);
            return build(n);
        }).forEach(city -> {
                threadPool.submit(() -> {
                String tName = Thread.currentThread().getName();
                DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
                transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
                try {
                    boolean save = erpCityService.save(city);
                    log.warn("线程{}保存{}成功", tName, city.getName());
                    if(!save) {
                        log.warn("线程{} setFalse{}", tName, 1);
                        flag.set(false);
                    }
                }
                catch (Exception e){
                    log.warn("线程{}保存{}失败{}", new Object[]{tName, city.getName(), e.getMessage()});
                    log.warn("线程{} setFalse{}", tName, 2);
                    flag.set(false);
                }
                finally {
                    log.warn("线程{}执行完成, await ...", tName);
                }
                downLatch.countDown();

                /**
                 * 核心问题还是数据库执行错误的时候，本身的SQL异常，会被锁住，然后抛出 Lock wait timeout exceeded
                 * [临时解决方案] 新增一个latch锁，确保判断flag的时候，所有线程都出结果了
                 */
                boolean timeout = downLatch.await(30L, TimeUnit.SECONDS);
                if (!timeout) {
                    flag.set(false);
                    log.warn("线程{} setFalse{}", tName, 3);
                }
                start.countDown();
                start.await();
                log.warn("所有线程执行完毕...");
                if (flag.get()) {
                    transactionManager.commit(transStatus);
                    log.warn("提交完毕");
                }else {
                    transactionManager.rollback(transStatus);
                    log.warn("回滚完毕");
                }
                return true;
            });
        });
        log.warn("--done---");
        return Result.ok("done");
    }


    /**
     * 类似 link{https://segmentfault.com/a/1190000042613305} 这种将 事务Status提出来批量回滚或提交，是不行的
     * 异常 : No value for key 。。。 bound to thread [http-nio-8381-exec-1]
     * 因为 : TransactionSynchronizationManager 中 getResource() 是从ThreadLocal中根据Thread获取Connection的
     * @see TransactionSynchronizationManager#getResource(java.lang.Object)
     * @param recordNum
     * @return
     */
    @RequestMapping("/batchInsert4")
    public Result<?> batchInsert4(@RequestParam Integer recordNum) {
        List<TransactionStatus> statusList = Collections.synchronizedList(new ArrayList<TransactionStatus>());
        List<Future<Boolean>> futureList = new ArrayList<>();

        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        IntStream.range(0, recordNum).mapToObj(t -> {
            int n = new Random().nextInt(recordNum);
            return build(n);
        }).forEach(city -> {
            Future<Boolean> future = threadPool.submit(() -> saveInThread3(city, statusList));
            futureList.add(future);
        });
        // 等待线程执行完毕
        while (threadPool.getCompletedTaskCount() < threadPool.getTaskCount() ){
            try {
                TimeUnit.MILLISECONDS.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AtomicBoolean flag = new AtomicBoolean(true);
        futureList.forEach(t -> {
            boolean tResult;
            try {
                tResult = t.get(10L, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error("exception when get :: " + e.getMessage());
                tResult = false;
            }
            if(!tResult) {
                flag.set(false);
            }
        });
        if (flag.get()) {
            statusList.forEach(t -> transactionManager.commit(t));
        }else {
            statusList.forEach(t-> transactionManager.rollback(t));
        }
        return Result.ok("done");
    }


    /**
     * 封装了线程batchRun 和 手动事务
     * 作死啊，，，
     * @param recordNum
     * @return
     */
    @RequestMapping("/batchInsert5")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Result<?> batchInsert5(@RequestParam Integer recordNum) {
        List<Integer> nums = IntStream.range(0, recordNum).mapToObj(t -> new Random().nextInt(recordNum)).collect(Collectors.toList());
        BatchRunner<Integer, String> batchRunner = new BatchRunner(nums, new Function<Integer, String>() {
            @Override
            public String apply(Integer rx) {
                ErpCity city = build(rx);
                runWithTransaction(city);
                return city.getName();
            }
        });
        batchRunner.run();
        if (batchRunner.isSuccess()) {
            commit();
            return Result.ok("全部执行成功!");
        }else {
            rollback();
            return Result.ok("存在部分失败，已全部回滚!");
        }
    }

    private ErpCity build(Integer id){
        ErpCity city = new ErpCity();
        city.setProvinceId(10000+id);
        city.setName("testCity" + id);
        return city;
    }


    @Override
    protected void process(ErpCity erpCity) {
        boolean flag = erpCityService.save(erpCity);
        log.warn("insert data={} , result={}", erpCity.getName() , flag);
    }
}
