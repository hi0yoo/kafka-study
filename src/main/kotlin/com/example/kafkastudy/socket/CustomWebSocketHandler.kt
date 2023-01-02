package com.example.kafkastudy.socket

import com.example.kafkastudy.producer.Producer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class CustomWebSocketHandler(
    private val producer: Producer
): TextWebSocketHandler() {

    @Value("\${spring.kafka.template.default-topic}")
    private lateinit var topicName: String

    companion object {
        private val logger = LoggerFactory.getLogger(CustomWebSocketHandler::class.java)

        private val webSocketSessions = LinkedHashSet<WebSocketSession>()
    }

    // 클라이언트로부터 메시지를 받으면 호출됨
    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val payload: String = (message.payload?:"null") as String
        producer.produce(topicName, payload)
    }

    // 웹 소켓 connection 생성 이후 호출됨
    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("==============================")
        logger.info("web-socket connection success")
        logger.info("session : \n$session")
        logger.info("==============================")

        webSocketSessions.add(session)
    }

    // 웹 소켓 connection 닫히면 호출됨
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info("==============================")
        logger.info("web-socket connection closed")
        logger.info("session : \n$session")
        logger.info("status : \n$status")
        logger.info("==============================")

        webSocketSessions.remove(session)
    }

    // 웹 소켓으로 연결된 모든 클라이언트들에게 메시지 전송
    fun sendToAll(payload: String) {
        for (webSocketSession in webSocketSessions) webSocketSession.sendMessage(TextMessage(payload))
    }
}