package com.ylw.rabbitmq.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ylw.rabbitmq.RabbitMQConnecUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmProducer {

    private static final String QUEUE_NAME = "test_trans_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1.获取连接
        Connection newConnection = RabbitMQConnecUtils.newConnection();

        // 2.创建通道
        Channel channel = newConnection.createChannel();

        // 3.创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // confirm机制
        channel.confirmSelect();
        String msg = "test confirm msg ...";

        // 4.发送消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("生产者发送消息:" + msg);
        if (!channel.waitForConfirms()) {
            System.out.println("消息发送失败!");
        } else {
            System.out.println("消息发送成功!");
        }
        channel.close();
        newConnection.close();
    }
}
