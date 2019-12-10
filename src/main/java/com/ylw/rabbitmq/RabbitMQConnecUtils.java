package com.ylw.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnecUtils {

    public static Connection newConnection() throws IOException, TimeoutException {
        // 1.定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 2.设置服务器地址
        factory.setHost("127.0.0.1");

        // 3.设置协议端口号
        factory.setPort(5672);

        // 4.设置vhost
        factory.setVirtualHost("OrderHost");

        // 5.设置用户名称
        factory.setUsername("OrderAdmin");

        // 6.设置用户密码
        factory.setPassword("123456");

        // 7.创建新的连接
        Connection newConnection = factory.newConnection();
        return newConnection;
    }
}
