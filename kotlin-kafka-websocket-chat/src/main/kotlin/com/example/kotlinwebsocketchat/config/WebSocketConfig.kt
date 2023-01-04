package com.example.kotlinwebsocketchat.config

import com.example.kotlinwebsocketchat.socket.CustomWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@EnableWebSocket
@Configuration
class WebSocketConfig(
    private val customWebSocketHandler: CustomWebSocketHandler
): WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(customWebSocketHandler, "ws/chat").setAllowedOrigins("*")
    }
}