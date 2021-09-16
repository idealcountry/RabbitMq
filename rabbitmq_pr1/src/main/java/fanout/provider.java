package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @author spacetim
 * @date 2021/9/16
 * @description
 */
public class provider {
    public static void main(String[] args) throws IOException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs", "fanout");

        channel.basicPublish("logs", "", null, "fanout message".getBytes());

        ConnectionUtils.closeConnection(channel, connection);

    }
}
