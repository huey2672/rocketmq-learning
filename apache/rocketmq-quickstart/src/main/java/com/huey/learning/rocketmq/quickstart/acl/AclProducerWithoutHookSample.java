package com.huey.learning.rocketmq.quickstart.acl;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class AclProducerWithoutHookSample {

    public static void main(String[] args) throws Exception {


        DefaultMQProducer producer = new DefaultMQProducer("ACL_PRODUCER");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message message = new Message("topicB", ("Hello, RocketMQ ACL!").getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult);

        producer.shutdown();

    }


}
