package com.huey.learning.rocketmq.quickstart.transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;

/**
 * @author huey
 */
public class MyTransactionListener implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object object) {

        LocalTransactionState transactionState;

        String messageKey = StringUtils.defaultString(message.getKeys());
        switch (messageKey) {

            // commits the No.1 message after executing local transaction
            case "1":
                transactionState = LocalTransactionState.COMMIT_MESSAGE;
                break;

            // rollbacks the No.2 message after executing local transaction
            case "2":
                transactionState = LocalTransactionState.ROLLBACK_MESSAGE;
                break;

            // the states of other messages are unknown, namely pending
            default:
                transactionState = LocalTransactionState.UNKNOW;
                break;

        }

        System.out.printf("[%s] At 2PC, the No.%s message's local transaction state is %s.\n",
                DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(new Date()), messageKey, transactionState);

        return transactionState;

    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

        LocalTransactionState transactionState;

        String messageKey = messageExt.getKeys();
        switch (messageKey) {

            // commits the No.3 message when checking local transaction
            case "3":
                transactionState = LocalTransactionState.COMMIT_MESSAGE;
                break;

            // rollbacks the No.4 message when checking local transaction
            case "4":
                transactionState = LocalTransactionState.ROLLBACK_MESSAGE;
                break;

            // the states of other messages still are unknown
            default:
                transactionState = LocalTransactionState.UNKNOW;
                break;

        }

        System.out.printf("[%s] At checking state, the No.%s message's local transaction state is %s.\n",
                DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(new Date()), messageKey, transactionState);

        return transactionState;
    }

}
