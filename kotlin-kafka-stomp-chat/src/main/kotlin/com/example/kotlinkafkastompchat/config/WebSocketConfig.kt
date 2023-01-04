package com.example.kotlinkafkastompchat.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // http://localhost:8080/ws-chat 을 통해 소켓 연결
        registry.addEndpoint("/ws-chat")
            .setAllowedOrigins("http://localhost:8080")
            .withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 클라이언트가 메시지 발행시 /pub/* 경로로 전송
        registry.setApplicationDestinationPrefixes("/pub")
        // 클라이언트가 메시지를 /sub/* 경로로 구독
        registry.enableSimpleBroker("/topic")
    }
}