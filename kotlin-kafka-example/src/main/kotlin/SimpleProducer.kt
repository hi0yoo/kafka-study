import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.Properties

class SimpleProducer {
    companion object {
        private val logger = LoggerFactory.getLogger(SimpleProducer::class.java)
        // 전송하고자 하는 토픽 이름
        private const val TOPIC_NAME = "test"
        // 전송하고자 하는 카프카 클러스터 서버의 host와 IP
        private const val BOOTSTRAP_SERVERS = "127.0.0.1:9092"

        @JvmStatic
        fun main(args: Array<String>) {
            // KafkaProducer 인스턴스를 생성하기 위한 프로듀서 옵션들을 key-value 형식으로 선언
            val configs = Properties()
            configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVERS
            // 메시지 키와 값을 직력화하기 위한 직렬화 클래스 선언
            configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
            configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name

            // 설정 정보를 담은 Properties를 통해 KafkaProducer 인스턴스 생성
            // ProducerRecord 전송시 사용
            val producer = KafkaProducer<String, String>(configs)

            val messageValue = "testMessage"
            // 카프카 브로커로 데이터를 보내기 위해 ProducerRecord 생성
            // 메시지 키를 선언하지 않았으므로 null로 설정되어 전송됨
            val record = ProducerRecord<String, String>(TOPIC_NAME, messageValue)
            // 파라미터로 들어간 record를 프로듀서 내부에 가지고 있다가 배치 형태로 묶어서 전송함.
            producer.send(record)
            logger.info("$record")
            // 프로듀서 내부 버퍼에 가지고 있던 레코드 배치를 브로커로 전송함
            producer.flush()
            // producer 인스턴스의 리소스를 안전하게 종료
            producer.close()
        }
    }
}
