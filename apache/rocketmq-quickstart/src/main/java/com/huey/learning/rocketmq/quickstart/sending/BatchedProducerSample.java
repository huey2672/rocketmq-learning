package com.huey.learning.rocketmq.quickstart.sending;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengzs
 */
public class BatchedProducerSample {


    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("BATCHED_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        // creates a collection of messages
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(new Message("BatchedTopic", ("Hello, RocketMQ!" + i).getBytes()));
        }

        // batches delivering the messages to one of the brokers
        SendResult sendResult = producer.send(messages);
        // prints the result
        System.out.println(sendResult);

        // stops the producer
        producer.shutdown();

    }

}
