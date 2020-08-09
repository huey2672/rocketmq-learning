package com.huey.learning.rocketmq.quickstart.consuming;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhengzs
 */
public class PullConsumerSample {

    private static final Map<MessageQueue, Long> OFFSET_TABLE = new HashMap<>();

    public static void main(String[] args) throws Exception {

        // creates a consumer instance with group name
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("TEST_PULL_CONSUMER");

        // specifies the name server addresses
        consumer.setNamesrvAddr("localhost:9876");

        // launches the consumer instance
        consumer.start();

        // fetches message queues from consumer according to the topic
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TestTopic");

        for (MessageQueue messageQueue : messageQueues) {

            System.out.printf("Consumes messages from the queue: %s%n", messageQueue);

            SINGLE_MQ:
            while (true) {

                try {

                    // pulls the messages, if no any message arrival, blocking some time
                    PullResult pullResult = consumer.pullBlockIfNotFound(messageQueue, null, getMessageQueueOffset(messageQueue), 32);
                    System.out.printf("%s%n", pullResult);

                    putMessageQueueOffset(messageQueue, pullResult.getNextBeginOffset());

                    switch (pullResult.getPullStatus()) {

                        // finds messages
                        case FOUND:
                            List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                            System.out.println(messageExtList);
                            break;

                        // the found messages are not matched
                        case NO_MATCHED_MSG:
                            break;

                        // no any new messages
                        case NO_NEW_MSG:
                            break SINGLE_MQ;

                        // illegal offset
                        case OFFSET_ILLEGAL:
                            break;

                        default:
                            break;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        // stops the consumer
        consumer.shutdown();

    }

    /**
     * gets offset of the specified message queue
     *
     * @param messageQueue
     * @return
     */
    private static long getMessageQueueOffset(MessageQueue messageQueue) {
        Long offset = OFFSET_TABLE.get(messageQueue);
        return ObjectUtils.defaultIfNull(offset, 0L);
    }

    /**
     * marks the message queue's offset
     *
     * @param messageQueue
     * @param offset
     */
    private static void putMessageQueueOffset(MessageQueue messageQueue, long offset) {
        OFFSET_TABLE.put(messageQueue, offset);
    }

}
