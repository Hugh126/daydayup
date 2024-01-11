package multiThread.threadpool;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;


public  class ThreadSafeWrite {

    static class VO{
        public Integer x;
        public VO(Integer x) {
            this.x = x;
        }
    }

    private Map<Integer, Collection<VO>> globalMap = new ConcurrentHashMap();



    /**
     * 测试不加锁操作同步Map中的集合Value，存在漏掉问题
     *
     * [解决办法] 使用 synchronized 或者 安全集合，如： CopyOnWriteArrayList CopyOnWriteArraySet
     */
    public int test1() {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        List<Future> fList = new ArrayList<>();
        IntStream.range(0, 10000).forEach(t-> {
            int a = t%2;
            Future f = pool.submit(()-> {
                Collection<VO> vos = globalMap.computeIfAbsent(a, k -> new CopyOnWriteArrayList<>());
//                synchronized (vos) {
                    vos.add(new VO(t));
//                }
            });
            fList.add(f);
        });
        fList.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        globalMap.forEach((k,v)-> {
            System.out.println(String.format("k=%d,vSize=%d", k,v.size()));
        });
        int sum = globalMap.values().stream().map(Collection::size).mapToInt(Integer::valueOf).sum();
        globalMap.clear();
        return sum;
    }

    @Test
    public void test2() {
//        Assert.assertEquals( 10000,test1());
        long cnt = IntStream.range(0, 50).map(x -> test1()).filter(t -> Objects.equals(t, 10000)).count();
        System.out.println(cnt);
        Assert.assertEquals(50, cnt);
    }
}
