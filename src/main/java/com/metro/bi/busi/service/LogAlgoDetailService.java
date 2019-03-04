package com.metro.bi.busi.service;

import com.metro.bi.busi.entity.AlgorithmInfo;
import com.metro.bi.busi.entity.LogAlgoDetail;

public interface LogAlgoDetailService {

    /**
     * @Description 新增
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param logAlgoDetail
     * @return int
     */
    int add(LogAlgoDetail logAlgoDetail);
    /**
     * @Description 更新
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param logAlgoDetail
     * @return int
     */
    int update(LogAlgoDetail logAlgoDetail);
}
