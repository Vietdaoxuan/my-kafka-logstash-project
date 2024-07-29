package Kafka.Producer.Controller;

import Kafka.Producer.Model.Order;
import Kafka.Producer.Service.LoggingService;
import Kafka.Producer.Service.OrderProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderProducer orderProducer;

    private final LoggingService loggingService;
    public OrderController(OrderProducer orderProducer, LoggingService loggingService) {
        this.orderProducer = orderProducer;
        this.loggingService = loggingService;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        orderProducer.sendMessage(message);
        return "Message sent to Kafka topic: " + message;
    }

    @GetMapping("/sendOrder")
    public String sendOrder(@RequestBody Order order) {
        try {
            orderProducer.sendOrder(order);
            loggingService.logMessage("End Send order");
            return "Message sent to Kafka topic: " + order;
        }
        catch (Exception ex){
            loggingService.logMessage(ex.getMessage());
            loggingService.logMessage("End Send order");
            return ex.getMessage();
        }
    }
}
