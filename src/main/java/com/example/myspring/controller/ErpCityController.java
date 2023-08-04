package com.example.myspring.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myspring.common.Result;
import com.example.myspring.entity.ErpCity;
import com.example.myspring.service.ErpCityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/city")
public class ErpCityController {

    @Autowired
    private ErpCityService erpCityService;

    /**
     * 仅仅是 PlatformTransactionManager事务处理的封装模板, 等同于方法外加上事务注解
     */
    @Autowired
    private TransactionTemplate transactionTemplate;



    @RequestMapping("/list")
    public Result<IPage<ErpCity>> page( @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        QueryWrapper<ErpCity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("env_type", envType);
        Page<ErpCity> page = new Page<>(pageNo, pageSize);
        IPage<ErpCity> pageList = erpCityService.page(page, queryWrapper);
        Result<IPage<ErpCity>> result = new Result();
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 异常测试
     * @param id
     * @param name
     * @return
     */
    @Transactional
    @RequestMapping("/{id}/updateName")
    public Result<?> update(@PathVariable Integer id, @RequestParam String name) throws ExecutionException, InterruptedException {
        ErpCity city = erpCityService.getById(id);
        city.setName("哈哈" + name);
        erpCityService.updateById(city);
        log.warn("[updateName1]{}", JSONUtil.toJsonStr(city));
        Future<?> future = Executors.newSingleThreadExecutor().submit(() -> {
            boolean b = erpCityService.updateByName(id, name);
            return b;
        });
        Object o = future.get();
        Result result = new Result();
        result.setSuccess(false);
        return result;
    }
    
    private ErpCity sample(Integer id){
        ErpCity city = new ErpCity();
        city.setProvinceId(10000+id);
        city.setName("testCity" + id);
        return city;
    }



    @RequestMapping("insert")
    public Result<?> insertWithTemplate(@RequestParam Integer recordNum) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<Integer>> collect = IntStream.range(0, recordNum).mapToObj(n -> executorService.submit((Callable<Integer>) () -> {
            int rx = new Random().nextInt(recordNum);
//            Boolean flag = erpCityService.save(sample(rx));
            Boolean flag = transactionTemplate.execute((TransactionCallback<Boolean>) status -> {
                // 异常线程互不影响
                return erpCityService.save(sample(rx));
            });
            log.warn("insert data={} , result={}", rx , flag);
            return rx;
        })).collect(Collectors.toList());
        List<Integer> end = collect.stream().map(t -> {
            try {
                return t.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return Result.ok(end);
    }

}
