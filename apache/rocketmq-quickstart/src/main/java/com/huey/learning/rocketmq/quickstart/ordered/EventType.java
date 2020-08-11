package com.huey.learning.rocketmq.quickstart.ordered;

import java.io.Serializable;

/**
 * @author huey
 */
public enum EventType implements Serializable {

    CREATION,
    PAYMENT,
    COMPLETION

}
