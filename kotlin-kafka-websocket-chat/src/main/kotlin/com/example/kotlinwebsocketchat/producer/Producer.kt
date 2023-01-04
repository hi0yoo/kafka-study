package com.example.kotlinwebsocketchat.producer

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class Producer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        private val logger = LoggerFactory.getLogger(Producer::class.java)
    }

    // 브로커에 데이터 적재
    fun produce(topic: String, payload: String) {
        logger.info("Producer - TOPIC: $topic, PAYLOAD: $payload")
        kafkaTemplate.send(topic, payload)
    }
}
