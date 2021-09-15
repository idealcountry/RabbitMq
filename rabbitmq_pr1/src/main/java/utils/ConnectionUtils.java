package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author spacetim
 * @date 2021/9/15
 * @description utils for RabbitMq Connection
 */
public class ConnectionUtils {

    private static  ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123456");
    }


    public static Connection getConnection(){
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();


            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Channel channel, Connection conn){

        try {
            if (channel != null) {
                channel.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}