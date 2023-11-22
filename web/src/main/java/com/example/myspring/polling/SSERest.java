package com.example.myspring.polling;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping(path = "/sse")
public class SSERest {

    @GetMapping(path = "/index")
    public String index() {
        return "sseIndex";
    }

    @ResponseBody
    @GetMapping(path = "subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter push(String id) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(60000L);
        sseEmitter.onCompletion(() -> log.warn("[推送完成]"));
        new Thread(() -> {
            IntStream.range(1, 10).forEach(n -> {
                try {
                    sseEmitter.send(SseEmitter.event().data("推送消息 : " + n ));
                    TimeUnit.MILLISECONDS.sleep(500L);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            try {
                // 注意消息的data是必须填的，也不能为空
                sseEmitter.send(SseEmitter.event().name("close").data("--anything but no null--"));
                // 这里完全不是关闭，只是清空异步响应DeferredResult
                sseEmitter.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
        return sseEmitter;
    }



}
