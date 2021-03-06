package com.huey.learning.rocketmq.quickstart.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author huey
 */
public class ProducerSample {


    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("SIMPLE_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        // creates a message
        Message message = new Message("SimpleTopic", ("Hello, RocketMQ!").getBytes());
        // delivers the message to one of the brokers
        SendResult sendResult = producer.send(message);
        // prints the result
        System.out.println(sendResult);

        // stops the producer
        producer.shutdown();

    }

}
