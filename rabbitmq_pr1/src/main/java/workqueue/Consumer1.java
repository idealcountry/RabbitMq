package workqueue;

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
        final Channel channel = connection.createChannel();

        channel.queueDeclare("work",true,false,false,null);

//        如果不设置通道的消息数量，还是依旧会接收平均分配的消息数量
        channel.basicQos(1);
        channel.basicConsume("work", false,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1：" + new String(body));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
