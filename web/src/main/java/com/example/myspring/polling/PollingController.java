package com.example.myspring.polling;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;

/**
 * @author hugh
 *
 * 通过Servlet3提供的DeferredResult，实现长轮询
 *
 */
@RequestMapping("/polling")
@RestController
public class PollingController {

    private final static Multimap<Integer, DeferredResult<String>> watchRequestMap = Multimaps.synchronizedMultimap(HashMultimap.create());
    private static final Long TIME_OUT = 60000L;

    @RequestMapping("watch/{id}")
    public DeferredResult<String> watch(@PathVariable Integer id) {
        DeferredResult<String> result = new DeferredResult<>(TIME_OUT);
        result.onTimeout(() -> {
            System.err.println("Task time out.");
        });
        result.onCompletion(() -> {
            watchRequestMap.remove(id, result);
        });
        watchRequestMap.put(id, result);
        return result;
    }


    @RequestMapping("publish/{id}")
    public void publish(@PathVariable Integer id) {
        Collection<DeferredResult<String>> deferredResults = watchRequestMap.get(id);
        deferredResults.forEach(item -> {
            item.setResult(String.format("id=%d于%s更新完成", id, DateUtil.now()));
        });
    }
}
