package com.example.myspring.polling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

@Controller
@RequestMapping("/websocket")
public class WebSocketRest {

    @Autowired
    private MyTextSocketHandler socketHandler;

    @GetMapping(path = "/index")
    public String websocket() {
        return "websocket";
    }


    @ResponseBody
    @GetMapping(path = "/push")
    public String push(@RequestParam String msg) throws Exception {
        Collection<WebSocketSession> values = MyTextSocketHandler.connectManager.values();
        for (WebSocketSession session : values) {
            socketHandler.handleTextMessage(session, new TextMessage(msg));
        }
        return  "push msg success.";
    }
}
