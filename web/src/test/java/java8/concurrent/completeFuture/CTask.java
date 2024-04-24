package java8.concurrent.completeFuture;

import com.example.myspring.common.Result;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CTask  {

    private List taskList = new ArrayList();
    private List<Result> resultList = new ArrayList<>();
    private Supplier<Result> supplier;

    public CTask(List taskList) {
        this.taskList = taskList;
    }

    CompletableFuture<Result> submit() {
        return CompletableFuture.supplyAsync(() -> {
            long t1 = System.currentTimeMillis();
            Result result = supplier.get();
            result.setCost((System.currentTimeMillis() - t1) / 1000.0);
            return result;
        }).handle((res, th) -> {
            if (th != null) {
                return null;
            }
            return res;
        });
    }


    public static void main(String[] args) {
        CTask task = new CTask(IntStream.range(1,5).mapToObj(Integer::valueOf).collect(Collectors.toList()));
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    }



}
