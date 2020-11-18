package com.fang.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author fang
 */
@Data
@Entity(name = "order_t")
public class Order {
    @Id
    private String orderId;
    private String chanId;
    private String productId;
    private String chanUserId;
    /**
     * @See com.fang.entity.enums.OrderType
     * */
    private String orderType;
    /**
     * @See com.fang.entity.enums.OrderStauts
     * */
    private String orderStauts;
    private String outerOrderId;
    private String memo;
    private BigDecimal amount;
    private Date createAt;
    private Date updateAt;
}
