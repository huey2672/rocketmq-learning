package com.huey.learning.rocketmq.quickstart.ordered;

import java.io.Serializable;

/**
 * order event
 *
 * @author huey
 */
public enum OrderEvent implements Serializable {

    /**
     * create
     */
    CREATION,

    /**
     * pay
     */
    PAYMENT,

    /**
     * complete
     */
    COMPLETION

}
