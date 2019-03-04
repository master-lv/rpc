package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.MethodInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


/**
 * Title: MethodInfo PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类 MethodInfo 的操作映射成为对应的持久化操作
 * @author admin
 * @created 2018-10-29
 */
public interface MethodInfoMapper {


    /**
     * @Description 根据 algo_code 和 method_code 查询
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoCode
     * @param methodCode
     * @return int
     */
    @Select("select count(1) from tbl_algo_method_info where algo_code = #{algoCode} and method_code = #{methodCode} ")
    int findByCode(@Param("algoCode") String algoCode,@Param("methodCode") String methodCode);
    /**
     * @Description 根据 algoName 和 methodName 查询
     * @author lvbq
     * @Date  2018-10-29 19:39
     * @param algoName
     * @param methodName
     * @return int
     */
    @Select(" select count(1) from tbl_algo_method_info where algo_code in (select algo_code from tbl_algo_algorithm_info where algo_name=#{algoName} ) and method_name = #{methodName} ")
    int findByName(@Param("algoName") String algoName,@Param("methodName") String methodName);
    /**
     * @Description 根据 app_code , algo_code 和 method_code 查询
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoCode
     * @param methodCode
     * @return int
     */
    @Select("select count(1) from tbl_am_group_method where method_code = #{methodCode} and  group_code in " +
            "(select group_code from tbl_am_group where id in (select group_id from tbl_am_app_group_relation where app_id in (select id from tbl_am_application where app_code = #{appCode} ))) ")
    int validAppAuth(@Param("appCode") String appCode,@Param("algoCode") String algoCode,@Param("methodCode") String methodCode);
    /**
     * @Description 根据 app_code , algo_code 和 method_code 查询
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoName
     * @param methodName
     * @return int
     */
    @Select("select count(1) from tbl_am_group_method where method_code in (select method_code from tbl_algo_method_info where method_name= #{methodName})  and  group_code in " +
            "(select group_code from tbl_am_group where id in (select group_id from tbl_am_app_group_relation where app_id in (select id from tbl_am_application where app_code = #{appCode} ))) ")
    int validAppAuthWithName(@Param("appCode") String appCode,@Param("algoName") String algoName,@Param("methodName") String methodName);
    /**
     * @description 根据method_code查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param methodCode
     * @return
     */
    @Select("select * from tbl_algo_method_info where method_code = #{methodCode} ")
    MethodInfo findByMethodCode(@Param("methodCode") String methodCode);
    /**
     * @description 根据method_name查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param methodName
     * @return
     */
    @Select("select id,method_name,method_code,algo_code from tbl_algo_method_info where method_name = #{methodName} ")
    @Results(
            {
                    @Result(id=true,column="id",property="id"),
                    @Result(column="method_name",property="methodName"),
                    @Result(column="method_code",property="methodCode"),
                    @Result(column="algo_code",property="algoCode")
            }
    )
    MethodInfo findByMethodName(@Param("methodName") String methodName);
}
