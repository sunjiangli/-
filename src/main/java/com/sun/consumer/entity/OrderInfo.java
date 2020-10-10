package com.sun.consumer.entity;

import java.io.Serializable;

/**
 * (OrderInfo)实体类
 *
 * @author makejava
 * @since 2020-10-09 16:15:05
 */
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = -88381203360411815L;
    /**
     * 主键ID
     */
    private String pkid;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 订单状态 0：未支付  1：已支付 2 已取消
     */
    private String orderStatus;


    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}