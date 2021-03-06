package com.huey.learning.rocketmq.quickstart.sending;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author huey
 */
public class AsyncProducerSample {

    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("ASYNC_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        // creates a message
        Message message = new Message("AsyncTopic", ("Hello, RocketMQ!").getBytes());

        // delivers the message asynchronously to one of the brokers
        producer.send(message, new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                // prints the result
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }

        });

        // waits for sending to complete
        TimeUnit.SECONDS.sleep(3);

        // stops the producer
        producer.shutdown();

    }

}
