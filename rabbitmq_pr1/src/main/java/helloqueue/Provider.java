package helloqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @author spacetim
 * @date 2021/9/15
 * @description
 */
public class Provider {

    @Test
    public void testSendMessage() throws IOException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("123456");
//
//        //创建连接
//        Connection connection = connectionFactory.newConnection();

        Connection connection = ConnectionUtils.getConnection();

        //创建信道
        Channel channel = connection.createChannel();

        channel.queueDeclare("hello",false,false,false,null);
        channel.basicPublish("", "hello", null, "hello rabbitmq".getBytes());


        ConnectionUtils.closeConnection(channel, connection);


    }
}
