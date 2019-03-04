package com.metro.bi.busi.service.impl;

import com.metro.bi.busi.entity.MethodInfo;
import com.metro.bi.busi.mapper.AlgorithmInfoMapper;
import com.metro.bi.busi.mapper.MethodInfoMapper;
import com.metro.bi.busi.service.MethodInfoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("methodInfoService")
public class MethodInfoServiceImpl implements MethodInfoService {

    private MethodInfoMapper methodInfoMapper;

    public MethodInfoMapper getMethodInfoMapper() {
        return methodInfoMapper;
    }

    @Resource
    // 默认按名称(algorithmInfoMapper)注入,若名称匹配失败，则按照类型匹配
    public void setMethodInfoMapper(MethodInfoMapper methodInfoMapper) {
        this.methodInfoMapper = methodInfoMapper;
    }

    /**
     * @Description 根据code查询指定应用
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param algoCode
     * @return int
     */
    public int findByCode(String algoCode,String methodCode){
        return methodInfoMapper.findByCode(algoCode,methodCode);
    }
    /**
     * @Description 根据code查询指定应用
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param algoName
     * @return int
     */
    public int findByName(String algoName,String methodName){
        return methodInfoMapper.findByName(algoName,methodName);
    }
    /**
     * @Description 验证应用权限
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoCode
     * @param methodCode
     * @return int
     */
    public int validAppAuth(String appCode,String algoCode,String methodCode){
        return methodInfoMapper.validAppAuth(appCode,algoCode,methodCode);
    }
    /**
     * @Description 验证应用权限
     * @author :DELL
     * @Date :2018/11/01 13:55
     * @param algoName
     * @param methodName
     * @return int
     */
    public int validAppAuthWithName(String appCode,String algoName,String methodName){
        return methodInfoMapper.validAppAuthWithName(appCode,algoName,methodName);
    }
    /**
     * @description 根据code查询指定方法
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param methodCode
     * @return AlgorithmInfo
     */
    public MethodInfo findByMethodCode(String methodCode){
        return methodInfoMapper.findByMethodCode(methodCode);
    }
    /**
     * @description 根据code查询指定方法
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param methodName
     * @return AlgorithmInfo
     */
    public MethodInfo findByMethodName(String methodName){
        return methodInfoMapper.findByMethodName(methodName);
    }
}
