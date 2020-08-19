package com.huey.learning.rocketmq.quickstart.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.stream.Stream;

/**
 * @author huey
 */
public class SqlFilterProducerSample {


    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("SQL_FILTER_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        for (int i = 0; i < 5; i++) {
            // creates a message
            Message message = new Message("SqlFilterTopic", ("Hello, x = " + i + "!").getBytes());
            // put a user property named x
            message.putUserProperty("x", String.valueOf(i));
            // delivers the message to one of the brokers
            SendResult sendResult = producer.send(message);
            // prints the result
            System.out.println(sendResult);
        }

        // stops the producer
        producer.shutdown();

    }

}
