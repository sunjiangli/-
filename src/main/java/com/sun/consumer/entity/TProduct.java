package com.sun.consumer.entity;

import java.io.Serializable;

/**
 * (TProduct)实体类
 *
 * @author makejava
 * @since 2020-09-27 10:45:41
 */
public class TProduct implements Serializable {
    private static final long serialVersionUID = -30196141359473751L;

    private Integer id;

    private String productno;

    private Integer total;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductno() {
        return productno;
    }

    public void setProductno(String productno) {
        this.productno = productno;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}