package com.metro.bi.busi.dao.algo;

import com.metro.bi.busi.entity.AlgorithmInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

interface AlgorithmInfoDao {
    /*获取所有记录*/
    List<AlgorithmInfo> getAll();
    /*通过ID获取单个数据*/
    AlgorithmInfo getById(@Param("id") int bid);
    /*添加数据*/
    int add(AlgorithmInfo entity);
    /*根据ID删除*/
    int del(int bid);
    /*更新*/
    int update(AlgorithmInfo entity);

}
