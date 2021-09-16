package direct;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @author spacetim
 * @date 2021/9/16
 * @description
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs_direct", "direct");
        // 临时队列
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queue, "logs_direct", "info");

        channel.basicConsume(queue, true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });

    }
}
