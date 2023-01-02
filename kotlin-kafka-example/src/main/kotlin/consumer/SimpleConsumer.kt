package consumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.Properties

class SimpleConsumer {

    companion object {
        private val logger = LoggerFactory.getLogger(SimpleConsumer::class.java)
        // 전송하고자 하는 토픽 이름
        private const val TOPIC_NAME = "test"
        // 전송하고자 하는 카프카 클러스터 서버의 host와 IP
        private const val BOOTSTRAP_SERVERS = "127.0.0.1:9092"
        private const val GROUP_ID = "test-group"

        @JvmStatic
        fun main(args: Array<String>) {
            val configs = Properties()
            configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVERS
            configs[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
            configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
            configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name

            val consumer = KafkaConsumer<String, String>(configs)
            consumer.subscribe(listOf(TOPIC_NAME))

            while (true) {
                val records = consumer.poll(Duration.ofSeconds(1))
                for (record in records) logger.info("$record")
            }
        }
    }
}