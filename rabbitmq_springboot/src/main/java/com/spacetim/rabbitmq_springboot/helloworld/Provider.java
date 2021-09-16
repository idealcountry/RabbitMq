package com.spacetim.rabbitmq_springboot.helloworld;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author spacetim
 * @date 2021/9/16
 * @description
 */
public class Provider {
    // 注入rabbitmqTemplate
    @Autowired
    private  RabbitTemplate rabbitTemplate;
    public static void main(String[] args) {


    }
}
