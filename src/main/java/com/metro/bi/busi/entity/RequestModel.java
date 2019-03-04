package com.metro.bi.busi.entity;


import java.util.Map;


public class RequestModel {

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAlgorithmCode() {
        return algorithmCode;
    }

    public void setAlgorithmCode(String algorithmCode) {
        this.algorithmCode = algorithmCode;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public int getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    private int type;
    private String appCode;
    private String algorithmCode;
    private String methodCode;
    private int checkFlag;
    private Map<String,Object> params;

    @Override
    public String toString(){
        StringBuffer ret=new StringBuffer();
        ret.append(",type:"+this.getType());
        ret.append(",appCode:"+this.getAppCode());
        ret.append(",algorithmCode:"+this.getAlgorithmCode());
        ret.append(",methodCode:"+this.getMethodCode());
        ret.append(",checkFlag:"+this.getCheckFlag());
        if(null!=params){
            for(String s:params.keySet()){
                ret.append(s+":"+params.get(s)+",");
            }
        }

        return ret.toString();
    }

}
