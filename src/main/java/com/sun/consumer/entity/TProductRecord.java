package com.sun.consumer.entity;

import java.io.Serializable;

/**
 * (TProductRecord)实体类
 *
 * @author makejava
 * @since 2020-09-27 10:46:51
 */
public class TProductRecord implements Serializable {
    private static final long serialVersionUID = -96599468826279342L;

    private Integer id;

    private String productno;

    private Integer userid;


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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}