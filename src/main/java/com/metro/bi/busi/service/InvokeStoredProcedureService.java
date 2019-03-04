package com.metro.bi.busi.service;

public interface InvokeStoredProcedureService {

    /**
     * @Description 根据code查询记录数
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoCode
     * @param methodCode
     * @return int
     */
    int findByCode(String algoCode, String methodCode);
    /**
     * @Description 验证应用权限
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoCode
     * @param methodCode
     * @return int
     */
    int validAppAuth(String appCode, String algoCode, String methodCode);
}
