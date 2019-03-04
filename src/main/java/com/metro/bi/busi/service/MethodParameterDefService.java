package com.metro.bi.busi.service;

import com.metro.bi.busi.entity.MethodParameterDef;

import java.util.List;

public interface MethodParameterDefService {

    /**
     * @description 根据code查询记录集
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param code
     * @return int
     */
    List<MethodParameterDef> findByCode(String code,String methodCode);
    /**
     * @description 根据name查询记录集
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param algorithmName
     * @return list
     */
    List<MethodParameterDef> findByName(String algorithmName,String methodName);

}
