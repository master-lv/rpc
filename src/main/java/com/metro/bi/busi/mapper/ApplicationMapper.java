package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.Application;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * Title: Application PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类Application的操作映射成为对应的持久化操作
 * @author admin
 * @created 2018-10-29
 */
public interface ApplicationMapper {


    /**
     * @description 根据app_code查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param appCode
     * @return
     */
    @Select("select count(1) from tbl_am_application where app_code = #{appCode} ")
    int findByCode(@Param("appCode") String appCode);

}
