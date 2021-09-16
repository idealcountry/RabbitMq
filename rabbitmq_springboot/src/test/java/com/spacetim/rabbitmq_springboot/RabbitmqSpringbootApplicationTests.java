package com.spacetim.rabbitmq_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqSpringbootApplicationTests {

    // 注入rabbitmqTemplate
    @Autowired
    public  RabbitTemplate rabbitTemplate;


    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend("hello","hello world");
    }

//    work
    @Test
    void testWork() {
        for (int i = 0; i < 10; i++) {

            rabbitTemplate.convertAndSend("work","hello world of work " + i);
        }
    }

}
