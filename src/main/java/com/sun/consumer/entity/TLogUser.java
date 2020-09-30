package com.sun.consumer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (TLogUser)实体类
 *
 * @author makejava
 * @since 2020-09-30 14:46:46
 */
public class TLogUser implements Serializable {
    private static final long serialVersionUID = -32811536895665603L;

    private Integer id;

    private Integer userid;

    private String log;

    private Date createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}