package com.ylw.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.ylw.rabbitmq.RabbitMQConnecUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static final String QUEUE_NAME = "add_order_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.获取连接
        Connection newConnection = RabbitMQConnecUtils.newConnection();

        // 2.获取通道
        Channel channel = newConnection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msgString = new String(body, "UTF-8");
                System.out.println("消费者获取消息->" + msgString);
            }
        };
        // 3.监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

    }
}
