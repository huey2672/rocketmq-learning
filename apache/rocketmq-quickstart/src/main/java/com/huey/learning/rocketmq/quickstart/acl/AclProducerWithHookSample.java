package com.huey.learning.rocketmq.quickstart.acl;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RPCHook;

/**
 * @author huey
 */
public class AclProducerWithHookSample {

    public static void main(String[] args) throws Exception {

        RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials("RocketMQ", "12345678"));
        DefaultMQProducer producer = new DefaultMQProducer("ACL_PRODUCER", rpcHook);
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message message = new Message("topicB", ("Hello, RocketMQ ACL!").getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult);

        producer.shutdown();

    }

}
