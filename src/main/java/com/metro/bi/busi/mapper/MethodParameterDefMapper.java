package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.AlgorithmInfo;
import com.metro.bi.busi.entity.MethodParameterDef;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Title: MethodParameterDef PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类 MethodParameterDef 的操作映射成为对应的持久化操作
 * @author lvbq
 * @date 2018-11-03
 */
public interface MethodParameterDefMapper {


    /**
     * @Description 根据 algo_code method_code 查询应用系统
     * @author lvbq
     * @date 2018-11-03 19:39
     * @param algoCode
     * @param methodCode
     * @return bean
     */
    @Select(" select * from tbl_algo_method_parameter_def where algo_code = #{algoCode} and method_code = #{methodCode} order by formal_param_attr asc,id asc  ")
    @Results(
            {
                    @Result(id=true,column="id",property="id"),
                    @Result(column="formal_param_type",property="formalParamType"),
                    @Result(column="formal_param_subclass",property="formalParamSubclass"),
                    @Result(column="formal_param_name",property="formalParamName"),
                    @Result(column="formal_param_length",property="formalParamLength"),
                    @Result(column="formal_param_precision",property="formalParamPrecision"),
                    @Result(column="algo_code",property="algoCode"),
                    @Result(column="method_code",property="methodCode"),
                    @Result(column="formal_param_attr",property="formalParamAttr"),
                    @Result(column="formal_param_parent",property="formalParamParent"),
                    @Result(column="param_style",property="paramStyle"),
                    @Result(column="descrip",property="descrip")
            }
    )
    List<MethodParameterDef> findByCode(@Param("algoCode") String algoCode, @Param("methodCode") String methodCode);
    /**
     * @Description 根据 algo_code method_code 查询应用系统
     * @author lvbq
     * @date 2018-11-03 19:39
     * @param algorithmName
     * @param methodName
     * @return bean
     */
    @Select(" select * from tbl_algo_method_parameter_def  where algo_code in (select algo_code from tbl_algo_algorithm_info where algo_name= #{algorithmName}) and method_code in (select method_code from tbl_algo_method_info where method_name= #{methodName}) order by formal_param_attr asc,id asc   ")
    @Results(
            {
                    @Result(id=true,column="id",property="id"),
                    @Result(column="formal_param_type",property="formalParamType"),
                    @Result(column="formal_param_subclass",property="formalParamSubclass"),
                    @Result(column="formal_param_name",property="formalParamName"),
                    @Result(column="formal_param_length",property="formalParamLength"),
                    @Result(column="formal_param_precision",property="formalParamPrecision"),
                    @Result(column="algo_code",property="algoCode"),
                    @Result(column="method_code",property="methodCode"),
                    @Result(column="formal_param_attr",property="formalParamAttr"),
                    @Result(column="formal_param_parent",property="formalParamParent"),
                    @Result(column="param_style",property="paramStyle"),
                    @Result(column="descrip",property="descrip"),
                    @Result(column="display_order",property="displayOrder"),
                    @Result(column="primitive_type_flag",property="primitiveTypeFlag")
            }
    )
    List<MethodParameterDef> findByName(@Param("algorithmName") String algorithmName, @Param("methodName") String methodName);

}
