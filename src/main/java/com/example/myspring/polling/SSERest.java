package com.example.myspring.polling;

import cn.hutool.core.date.DateUtil;
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

@Controller
@RequestMapping(path = "/sse")
public class SSERest {

    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    @GetMapping(path = "")
    public String index() {
        return "sseIndex";
    }

    @ResponseBody
    @GetMapping(path = "subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter push(String id) throws IOException {
        // 超时时间设置为3s，用于演示客户端自动重连
        SseEmitter sseEmitter = new SseEmitter(60000L);
        // 设置前端的重试时间为1s
        sseEmitter.send(SseEmitter.event().reconnectTime(3000).data("连接成功" +  DateUtil.now()));
        doSomething();
        sseEmitter.send(SseEmitter.event().reconnectTime(1000).data("推送消息111 " +  DateUtil.now()));
        doSomething();
        sseEmitter.send(SseEmitter.event().reconnectTime(2000).data("推送消息222 " +  DateUtil.now()));
        sseCache.put(id, sseEmitter);
        sseEmitter.onTimeout(() -> {
            System.out.println(id + "超时");
            sseCache.remove(id);
        });
        sseEmitter.onCompletion(() -> System.out.println("完成！！！"));
        return sseEmitter;
    }


    private void doSomething() {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
