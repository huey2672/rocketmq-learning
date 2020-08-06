package com.huey.learning.rocketmq.quickstart.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author zhengzs
 */
public class SyncProducerSample {


    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("TEST_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        for (int i = 0; i < 10; i++) {

            // creates a message
            Message message = new Message("TestTopic", ("Hello RocketMQ " + i).getBytes());
            // delivers the message to one of the brokers
            SendResult sendResult = producer.send(message);
            // prints the result
            System.out.println(sendResult);

        }

    }

}
