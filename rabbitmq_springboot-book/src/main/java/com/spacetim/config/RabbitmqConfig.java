package com.spacetim.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * RabbitMQ自定义注入Bean配置
 * Created by steadyjack on 2019/3/30.
 */
@Configuration
public class RabbitmqConfig {

    private static final Logger log= LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){

        System.out.println("helllllllllllllllllllllllllllll");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置消息类型的转换
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);
        return factory;
    }

    /**
     * RabbitMQ发送消息的操作组件实例
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        /*rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });*/
        rabbitTemplate.setConfirmCallback((correlationData, ack,cause) -> {
            log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    //定义读取配置文件的环境变量实例
    @Autowired
    private Environment env;

    /**创建简单消息模型：队列、交换机和路由 **/

    //创建队列
    @Bean(name = "basicQueue")
    public Queue basicQueue(){
        return new Queue(env.getProperty("mq.basic.info.queue.name"),true);
    }

    //创建交换机：在这里以DirectExchange为例，在后面章节中我们将继续详细介绍这种消息模型
    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"),true,false);
    }

    //创建队列与交换机的绑定
    @Bean
    public Binding basicBinding(){
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));
    }

    /**创建简单消息模型-对象类型：队列、交换机和路由 **/

    //创建队列
    @Bean(name = "objectQueue")
    public Queue objectQueue(){
        return new Queue(env.getProperty("mq.object.info.queue.name"),true);
    }

    //创建交换机：在这里以DirectExchange为例，在后面章节中我们将继续详细介绍这种消息模型
    @Bean
    public DirectExchange objectExchange(){
        return new DirectExchange(env.getProperty("mq.object.info.exchange.name"),true,false);
    }

    //创建绑定
    @Bean
    public Binding objectBinding(){
        return BindingBuilder.bind(objectQueue()).to(objectExchange()).with(env.getProperty("mq.object.info.routing.key.name"));
    }


}






















