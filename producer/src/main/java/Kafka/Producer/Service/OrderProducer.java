package Kafka.Producer.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducer {
    @Value("${kafka.topic.name}")
    private String topicName;
    private final LoggingService loggingService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate, LoggingService loggingService) {
        this.kafkaTemplate = kafkaTemplate;
        this.loggingService = loggingService;
    }

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, "orderKey", message).toCompletableFuture();

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                loggingService.logMessage("Sent order: " + message);
            } else {
                loggingService.logMessage("Failed to send order: " + message + ", due to: " + ex.getMessage());
            }
        });
    }

    public void sendOrder(Object order) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, "orderKey", order).toCompletableFuture();

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                loggingService.logMessage("Sent order: " + order);
            } else {
                loggingService.logMessage("Failed to send order: " + order + ", due to: " + ex.getMessage());
            }
        });
    }

//    @KafkaListener(topics = "${kafka.topic.acknowledgments}", groupId = "ack-group")
//    public void listenForAcknowledgment(String acknowledgment) {
//        System.out.println("Received acknowledgment: " + acknowledgment);
//    }
}
