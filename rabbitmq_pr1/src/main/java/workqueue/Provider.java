package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @author spacetim
 * @date 2021/9/16
 * @description
 */
public class Provider {
    public static void main(String[] args) throws IOException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("work",true,false,false,null);

        for(int i = 0;i < 30;i++){
            channel.basicPublish("", "work", null, (i + "hello workqueue").getBytes());
        }


        ConnectionUtils.closeConnection(channel, connection);
    }
}
