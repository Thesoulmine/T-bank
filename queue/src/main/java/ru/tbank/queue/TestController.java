package ru.tbank.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final RabbitTemplate rabbitTemplate;

    public TestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/test-produce")
    public void testProduce() {
        for (int i = 0; i < 100; i++) {
        rabbitTemplate.send("test", new Message("Hello".getBytes()));
        }
    }

    @RabbitListener(queues = {"test"})
    public void listen1(String message) {
        System.out.println(message + "1");
    }

    @RabbitListener(queues = {"test"})
    public void listen2(String message) {
        System.out.println(message + "2");
    }

    @RabbitListener(queues = {"test"})
    public void listen3(String message) {
        System.out.println(message + "3");
    }
}
