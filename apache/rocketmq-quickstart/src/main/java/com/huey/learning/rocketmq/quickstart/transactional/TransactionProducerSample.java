package com.huey.learning.rocketmq.quickstart.transactional;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author huey
 */
public class TransactionProducerSample {

    public static void main(String[] args) throws Exception {

        // creates a transaction producer instance with group name
        TransactionMQProducer producer = new TransactionMQProducer("TRANSACTION_PRODUCER");
        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // registers a transaction listener
        producer.setTransactionListener(new MyTransactionListener());

        // launches the producer instance
        producer.start();

        for (int i = 1; i <= 5; i++) {

            // creates a message
            Message message = new Message("TransactionTopic", null, String.valueOf(i),
                    ("Hello, RocketMQ! No." + i).getBytes());
            // sends the message in transaction
            TransactionSendResult sendResult = producer.sendMessageInTransaction(message, null);

        }

        // pauses
        System.in.read();

        // stops the producer
        producer.shutdown();

    }

}
