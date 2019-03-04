package com.metro.bi.busi.entity;

import java.util.Date;

public class TaskTask {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPriors() {
        return taskPriors;
    }

    public void setTaskPriors(String taskPriors) {
        this.taskPriors = taskPriors;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceParams() {
        return serviceParams;
    }

    public void setServiceParams(String serviceParams) {
        this.serviceParams = serviceParams;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    private int id   ;
    private String taskCode;
    private String taskName;
    private String taskPriors;
    private String taskType;
    private long elapsedTime; //second
    private String cronExpression;
    private String serviceUrl;
    private String serviceParams;
    private String methodCode;
    private String descrip          ;


}
