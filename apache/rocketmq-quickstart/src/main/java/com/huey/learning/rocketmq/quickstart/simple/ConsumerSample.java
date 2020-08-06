package com.huey.learning.rocketmq.quickstart.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author zhengzs
 */
public class ConsumerSample {

    public static void main(String[] args) throws Exception {

        // creates a consumer instance with group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TEST_CONSUMER");

        // specifies the name server addresses
        consumer.setNamesrvAddr("localhost:9876");

        // subscribes a topic to consume
        consumer.subscribe("TestTopic", "*");

        // registers a listener to consume messages
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receives new messages: %s %n", Thread.currentThread().getName(), messageExtList);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // launches the consumer instance
        consumer.start();

    }

}
