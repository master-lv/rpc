package com.metro.bi.busi.service.impl;

import com.metro.bi.busi.mapper.MethodInfoMapper;
import com.metro.bi.busi.service.InvokeStoredProcedureService;
import com.metro.bi.busi.service.MethodInfoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("invokeStoredProcedureService")
public class InvokeStoredProcedureServiceImpl implements InvokeStoredProcedureService {

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
}
