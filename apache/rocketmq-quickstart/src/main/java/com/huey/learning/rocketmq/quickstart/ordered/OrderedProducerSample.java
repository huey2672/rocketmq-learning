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
 * @author zhengzs
 */
public class OrderedProducerSample {

    public static void main(String[] args) throws Exception {

        // creates a producer instance with group name
        DefaultMQProducer producer = new DefaultMQProducer("ORDERED_PRODUCER");

        // specifies the name server addresses
        producer.setNamesrvAddr("localhost:9876");

        // launches the producer instance
        producer.start();

        List<OrderEvent> orderEventList = buildOrderEvents();

        for (OrderEvent orderEvent : orderEventList) {

            Message message = new Message("OrderedTopic", SerializationUtils.serialize(orderEvent));
            // delivers the message to one of the brokers
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> messageQueues, Message message, Object arg) {
                    int orderId = (int) arg;
                    int index = orderId % messageQueues.size();
                    return messageQueues.get(index);
                }
            }, orderEvent.getOrderId());
            // prints the result
            System.out.println(sendResult);

        }

        // stops the producer
        producer.shutdown();

    }

    private static List<OrderEvent> buildOrderEvents() {

        List<OrderEvent> orderEventList = new ArrayList<>();

        orderEventList.add(new OrderEvent(1001, EventType.CREATION));
        orderEventList.add(new OrderEvent(2001, EventType.CREATION));
        orderEventList.add(new OrderEvent(1001, EventType.PAYMENT));
        orderEventList.add(new OrderEvent(3001, EventType.CREATION));
        orderEventList.add(new OrderEvent(3001, EventType.PAYMENT));
        orderEventList.add(new OrderEvent(2001, EventType.PAYMENT));
        orderEventList.add(new OrderEvent(4001, EventType.CREATION));
        orderEventList.add(new OrderEvent(4001, EventType.PAYMENT));
        orderEventList.add(new OrderEvent(4001, EventType.COMPLETION));
        orderEventList.add(new OrderEvent(3001, EventType.COMPLETION));
        orderEventList.add(new OrderEvent(1001, EventType.COMPLETION));
        orderEventList.add(new OrderEvent(2001, EventType.COMPLETION));

        return orderEventList;

    }

}
