package direct;

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

        channel.exchangeDeclare("logs_direct", "direct");

        String routingKey = "error";

        channel.basicPublish("logs_direct",routingKey, null, (routingKey + " of direct message").getBytes());

        ConnectionUtils.closeConnection(channel, connection);

    }
}
