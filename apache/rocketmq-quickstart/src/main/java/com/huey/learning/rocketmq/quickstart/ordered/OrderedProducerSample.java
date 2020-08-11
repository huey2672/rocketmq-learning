package com.huey.learning.rocketmq.quickstart.ordered;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huey
 */
public class OrderedProducerSample {

    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("ORDERED_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        // creates a collection of order messages
        List<OrderMessage> orderMessageList = createOrderMessages();

        for (OrderMessage orderMessage : orderMessageList) {

            // create mq message with order message
            Message message = new Message("OrderedTopic", SerializationUtils.serialize(orderMessage));

            // delivers the message with a queue selector to one of the brokers
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> messageQueues, Message message, Object arg) {
                    // puts the messages with the same order id to the same queue
                    int orderId = (int) arg;
                    int index = orderId % messageQueues.size();
                    return messageQueues.get(index);
                }
            }, orderMessage.getOrderId());

            // prints the result
            System.out.println(sendResult);

        }

        // stops the producer
        producer.shutdown();

    }

    /**
     * creates a collection of order messages
     *
     * @return
     */
    private static List<OrderMessage> createOrderMessages() {

        List<OrderMessage> orderMessageList = new ArrayList<>();

        orderMessageList.add(new OrderMessage(1001, OrderEvent.CREATION));
        orderMessageList.add(new OrderMessage(1002, OrderEvent.CREATION));
        orderMessageList.add(new OrderMessage(1001, OrderEvent.PAYMENT));
        orderMessageList.add(new OrderMessage(1003, OrderEvent.CREATION));
        orderMessageList.add(new OrderMessage(1003, OrderEvent.PAYMENT));
        orderMessageList.add(new OrderMessage(1002, OrderEvent.PAYMENT));
        orderMessageList.add(new OrderMessage(1004, OrderEvent.CREATION));
        orderMessageList.add(new OrderMessage(1004, OrderEvent.PAYMENT));
        orderMessageList.add(new OrderMessage(1004, OrderEvent.COMPLETION));
        orderMessageList.add(new OrderMessage(1003, OrderEvent.COMPLETION));
        orderMessageList.add(new OrderMessage(1001, OrderEvent.COMPLETION));
        orderMessageList.add(new OrderMessage(1002, OrderEvent.COMPLETION));

        return orderMessageList;

    }

}
