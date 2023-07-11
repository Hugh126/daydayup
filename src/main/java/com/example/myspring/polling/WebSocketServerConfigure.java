package com.example.myspring.polling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketServerConfigure implements WebSocketConfigurer {
    @Autowired
    private MyTextSocketHandler textSocketHandler;
    /**
     * 效果类似 @ServerEndpoint
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textSocketHandler, "/connect")
                .setAllowedOriginPatterns("*")  // setAllowedOrigins已过时
                .withSockJS();
    }
}