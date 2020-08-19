package com.huey.learning.rocketmq.quickstart.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author huey
 */
public class TagFilterConsumerSample {

    public static void main(String[] args) throws Exception {

        // creates a consumer instance with group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TAG_FILTER_CONSUMER");

        // specifies the name server addresses
        consumer.setNamesrvAddr("localhost:9876");

        // subscribes a topic with tags to consume
        consumer.subscribe("TagFilterTopic", "TagA || TagC || TagE");

        // registers a listener to consume messages
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
                messageExtList.forEach(messageExt -> {
                    System.out.printf("Receives a new message: %s %n", new String(messageExt.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // launches the consumer instance
        consumer.start();

        // pause and presses any keys to go on
        System.in.read();

        // stops the consumer
        consumer.shutdown();

    }

}
