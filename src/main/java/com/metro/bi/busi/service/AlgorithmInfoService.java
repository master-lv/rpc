package com.metro.bi.busi.service;

import com.metro.bi.busi.entity.AlgorithmInfo;

public interface AlgorithmInfoService {

    /**
     * @Description 根据code查询记录数
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param code
     * @return int
     */
    int findByCode(String code);
    /**
     * @Description 根据code查询记录数
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param code
     * @return AlgorithmInfo
     */
    AlgorithmInfo findByAlgoCode(String code);
    /**
     * @Description 根据name查询记录数
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param name
     * @return AlgorithmInfo
     */
    AlgorithmInfo findBeanByAlgoName(String name);
    /**
     * @Description 根据name查询记录数
     * @author DELL
     * @Date 2018/11/01 13:55
     * @param name
     * @return int
     */
    int findByAlgoName(String name);
}
