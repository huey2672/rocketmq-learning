package com.huey.learning.rocketmq.quickstart.ordered;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhengzs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent implements Serializable {

    /**
     * order id
     */
    private Integer orderId;

    /**
     * event type
     */
    private EventType eventType;

}
