package com.metro.bi.busi.service;

import com.metro.bi.busi.entity.MethodInfo;

public interface MethodInfoService {

    /**
     * @Description 根据code查询记录数
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoCode
     * @param methodCode
     * @return int
     */
    int findByCode(String algoCode,String methodCode);
    /**
     * @Description 根据name查询记录数
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoName
     * @param methodCode
     * @return int
     */
    int findByName(String algoName,String methodCode);
    /**
     * @Description 验证应用权限
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoCode
     * @param methodCode
     * @return int
     */
    int validAppAuth(String appCode,String algoCode,String methodCode);
    /**
     * @Description 验证应用权限
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoName
     * @param methodName
     * @return int
     */
    int validAppAuthWithName(String appCode,String algoName,String methodName);
    /**
     * @description 根据code查询指定方法
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param methodCode
     * @return AlgorithmInfo
     */
    MethodInfo findByMethodCode(String methodCode);
    /**
     * @description 根据name查询指定方法
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param methodName
     * @return AlgorithmInfo
     */
    MethodInfo findByMethodName(String methodName);
}
