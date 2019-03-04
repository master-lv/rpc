package com.metro.bi.busi.service.impl;


import com.metro.bi.busi.entity.AlgorithmInfo;
import com.metro.bi.busi.entity.LogAlgoDetail;
import com.metro.bi.busi.mapper.AlgorithmInfoMapper;
import com.metro.bi.busi.mapper.LogAlgoDetailMapper;
import com.metro.bi.busi.service.AlgorithmInfoService;
import com.metro.bi.busi.service.LogAlgoDetailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("logAlgoDetailService")
public class LogAlgoDetailServiceImpl implements LogAlgoDetailService {

    private LogAlgoDetailMapper logAlgoDetailMapper;

    public LogAlgoDetailMapper getLogAlgoDetailMapper() {
        return logAlgoDetailMapper;
    }

    @Resource
    // 默认按名称(logAlgoDetailMapper)注入,若名称匹配失败，则按照类型匹配
    public void setLogAlgoDetailMapper(LogAlgoDetailMapper logAlgoDetailMapper) {
        this.logAlgoDetailMapper = logAlgoDetailMapper;
    }

    /**
     * @Description 新增
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param logAlgoDetail
     * @return int
     */
    public int add(LogAlgoDetail logAlgoDetail){
        return logAlgoDetailMapper.insert(logAlgoDetail);
    }
    /**
     * @Description 更新
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param logAlgoDetail
     * @return int
     */
    public int update(LogAlgoDetail logAlgoDetail){
        return logAlgoDetailMapper.update(logAlgoDetail);
    }
}
