package com.metro.bi.busi.entity;

import java.util.Date;

public class Application {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescrip() {
        return appDescrip;
    }

    public void setAppDescrip(String appDescrip) {
        this.appDescrip = appDescrip;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private int id;
    private String appCode;
    private String appName;
    private String appDescrip;
    private String creater;
    private Date createTime;
}
