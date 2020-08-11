package com.huey.learning.rocketmq.quickstart.ordered;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * order message
 *
 * @author huey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {

    /**
     * order id
     */
    private Integer orderId;

    /**
     * event
     */
    private OrderEvent event;

    // other fields...

}
