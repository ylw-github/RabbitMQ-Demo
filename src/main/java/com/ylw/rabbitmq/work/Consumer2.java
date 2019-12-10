package com.ylw.rabbitmq.work;

import com.rabbitmq.client.*;
import com.ylw.rabbitmq.RabbitMQConnecUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {
    private static final String QUEUE_NAME = "add_order_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.获取连接
        Connection newConnection = RabbitMQConnecUtils.newConnection();
        // 2.获取通道
        final Channel channel = newConnection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);// 保证一次只分发一次 限制发送给同一个消费者 不得超过一条消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msgString = new String(body, "UTF-8");
                System.out.println("消费者获取消息:" + msgString);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 手动回执消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 3.监听队列
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);

    }
}
