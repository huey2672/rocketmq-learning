package com.huey.learning.rocketmq.quickstart.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.stream.Stream;

/**
 * @author huey
 */
public class TagFilterProducerSample {

    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("TAG_FILTER_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        Stream.of("TagA", "TagB", "TagC", "TagD", "TagE").forEach(
                tag -> {
                    try {
                        // creates a message with tag
                        Message message = new Message("TagFilterTopic", tag, ("Hello, " + tag + "!").getBytes());
                        // delivers the message to one of the brokers
                        SendResult sendResult = producer.send(message);
                        // prints the result
                        System.out.println(sendResult);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        // stops the producer
        producer.shutdown();

    }

}
