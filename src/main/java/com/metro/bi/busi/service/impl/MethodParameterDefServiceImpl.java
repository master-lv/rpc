package com.metro.bi.busi.service.impl;


import com.metro.bi.busi.entity.AlgorithmInfo;
import com.metro.bi.busi.entity.MethodParameterDef;
import com.metro.bi.busi.mapper.AlgorithmInfoMapper;
import com.metro.bi.busi.mapper.MethodParameterDefMapper;
import com.metro.bi.busi.service.AlgorithmInfoService;
import com.metro.bi.busi.service.MethodParameterDefService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("methodParameterDefService")
public class MethodParameterDefServiceImpl implements MethodParameterDefService {

    private MethodParameterDefMapper methodParameterDefMapper;

    public MethodParameterDefMapper getMethodParameterDefMapper() {
        return methodParameterDefMapper;
    }

    @Resource
    // 默认按名称(methodParameterDefMapper)注入,若名称匹配失败，则按照类型匹配
    public void setMethodParameterDefMapper(MethodParameterDefMapper methodParameterDefMapper) {
        this.methodParameterDefMapper = methodParameterDefMapper;
    }

    /**
     * @description 根据code查询形参定义
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param algoCode
     * @return AlgorithmInfo
     */
    public List<MethodParameterDef> findByCode(String algoCode, String methodCode){
        return methodParameterDefMapper.findByCode(algoCode,methodCode);
    }
    /**
     * @Description 根据name查询形参定义
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param algorithmName
     * @return AlgorithmInfo
     */
    public List<MethodParameterDef> findByName(String algorithmName, String methodName){
        return methodParameterDefMapper.findByName(algorithmName,methodName);
    }
}
