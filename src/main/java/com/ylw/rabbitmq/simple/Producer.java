package com.ylw.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ylw.rabbitmq.RabbitMQConnecUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String QUEUE_NAME = "add_order_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.获取连接
        Connection newConnection = RabbitMQConnecUtils.newConnection();
        // 2.创建通道
        Channel channel = newConnection.createChannel();
        // 3.创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "{'ordername':'云南白药牙膏'，‘price’:'39','weight':'480g'}";
        System.out.println("生产者发送消息:" + msg);
        // 4.发送消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        channel.close();
        newConnection.close();
    }

}