package com.huey.learning.rocketmq.quickstart.sending;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengzs
 */
public class OnewayProducerSample {


    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("ONEWAY_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        // creates a message
        Message message = new Message("OnewayTopic", ("Hello, RocketMQ!").getBytes());
        // delivers the message to one of the brokers
        producer.sendOneway(message);

        // waits for sending to complete
        TimeUnit.SECONDS.sleep(3);

        // stops the producer
        producer.shutdown();

    }

}
