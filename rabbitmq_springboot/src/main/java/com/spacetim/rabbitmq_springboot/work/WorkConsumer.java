package com.spacetim.rabbitmq_springboot.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author spacetim
 * @date 2021/9/16
 * @description
 */
@Component
public class WorkConsumer {


    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void reveive1(String message){
        System.out.println("message1 = " + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void reveive2(String message){
        System.out.println("message2 = " + message);
    }

}
