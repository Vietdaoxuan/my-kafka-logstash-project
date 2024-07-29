package Kafka.Consumer.Service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @Value("${kafka.topic.acknowledgments}")
    private String acknowledgmentsTopic;

    @Value("${kafka.topic.acknowledgments}")
    private String consumerInstance;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.println("Received message: " + record.value() + " at " + record.topic() + ", partition " + record.partition());

        // Acknowledge the message
//        acknowledgment.acknowledge();

        // Gửi phản hồi về topic acknowledgments
//        kafkaTemplate.send(acknowledgmentsTopic, "Order : " + record.value() + " has been processed.");

    }
}
