import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author spacetim
 * @date 2021/9/15
 * @description
 */
public class Receiver {

    private static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

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
        final Channel channel = connection.createChannel();
        channel.queueDeclare("hello",false,false,false,null);

        // 定义队列的消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive message:  " + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 监听队列
        channel.basicConsume(QUEUE_NAME,defaultConsumer);


//        延时回调函数
        TimeUnit.SECONDS.sleep(5);

        //        不关闭通道等待回调
        ConnectionUtils.closeConnection(channel, connection);

    }
}
