package com.example.kotlinwebsocketchat.consumer

import com.example.kotlinwebsocketchat.socket.CustomWebSocketHandler
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.Properties
import javax.annotation.PostConstruct

@Component
class Consumer(
    private val customWebSocketHandler: CustomWebSocketHandler
) {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootStrapServer: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Value("\${spring.kafka.consumer.value-deserializer}")
    private lateinit var valueDeserializer: String

    @Value("\${spring.kafka.consumer.key-deserializer}")
    private lateinit var keyDeserializer: String

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private lateinit var offsetReset: String

    @Value("\${spring.kafka.consumer.max-poll-records}")
    private var maxPollRecords: Int = 0

    @Value("\${spring.kafka.consumer.enable-auto-commit}")
    private var enableAutoCommit: Boolean = false

    private lateinit var kafkaConsumer: KafkaConsumer<String, String>

    companion object {
        private val logger = LoggerFactory.getLogger(Consumer::class.java)
    }

    @PostConstruct
    fun build() {
        val properties = Properties()
        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootStrapServer
        properties[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = keyDeserializer
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = valueDeserializer
        properties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = offsetReset
        properties[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = maxPollRecords
        properties[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = enableAutoCommit

        kafkaConsumer = KafkaConsumer(properties)
    }

    // 구독중인 토픽에 데이터가 적재되면 호출됨
    @KafkaListener(topics = ["\${spring.kafka.template.default-topic}"])
    fun consume(@Headers headers: MessageHeaders,
                @Payload payload: String) {
        logger.info("CONSUME - HEADERS: $headers, PAYLOAD: $payload")
        customWebSocketHandler.sendToAll(payload)
    }
}