package com.huey.learning.rocketmq.quickstart.ordered;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author huey
 */
public class OrderedConsumerSample {

    public static void main(String[] args) throws Exception {

        // creates a consumer instance with group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("SIMPLE_CONSUMER");

        // specifies the name server addresses
        consumer.setNamesrvAddr("localhost:9876");

        // subscribes a topic to consume
        consumer.subscribe("OrderedTopic", "*");

        // registers a listener to consume messages
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {

                for (MessageExt messageExt : messageExtList) {
                    OrderMessage orderMessage = SerializationUtils.deserialize(messageExt.getBody());
                    System.out.println("Deals with the order message: " + orderMessage);
                }

                return ConsumeOrderlyStatus.SUCCESS;
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
