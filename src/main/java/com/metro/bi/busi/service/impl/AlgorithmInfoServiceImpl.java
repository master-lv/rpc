package com.metro.bi.busi.service.impl;


import com.metro.bi.busi.entity.AlgorithmInfo;
import com.metro.bi.busi.mapper.AlgorithmInfoMapper;
import com.metro.bi.busi.mapper.ApplicationMapper;
import com.metro.bi.busi.service.AlgorithmInfoService;
import com.metro.bi.busi.service.ApplicationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("algorithmInfoService")
public class AlgorithmInfoServiceImpl implements AlgorithmInfoService {

    private AlgorithmInfoMapper algorithmInfoMapper;

    public AlgorithmInfoMapper getAlgorithmInfoMapper() {
        return algorithmInfoMapper;
    }

    @Resource
    // 默认按名称(algorithmInfoMapper)注入,若名称匹配失败，则按照类型匹配
    public void setAlgorithmInfoMapper(AlgorithmInfoMapper algorithmInfoMapper) {
        this.algorithmInfoMapper = algorithmInfoMapper;
    }

    /**
     * @description 根据code查询指定应用
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param code
     * @return int
     */
    public int findByCode(String code){
        return algorithmInfoMapper.findByCode(code);
    }
    /**
     * @description 根据code查询指定应用
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param code
     * @return AlgorithmInfo
     */
    public AlgorithmInfo findByAlgoCode(String code){
        return algorithmInfoMapper.findByAlgoCode(code);
    }
    /**
     * @description 根据name查询指定应用
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param name
     * @return AlgorithmInfo
     */
    public AlgorithmInfo findBeanByAlgoName(String name){
        return algorithmInfoMapper.findBeanByAlgoName(name);
    }
    /**
     * @Description 根据name查询指定应用
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param name
     * @return int
     */
    public int findByAlgoName(String name){
        return algorithmInfoMapper.findByAlgoName(name);
    }
}
