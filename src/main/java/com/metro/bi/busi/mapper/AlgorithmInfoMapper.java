package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.AlgorithmInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


/**
 * Title: AlgorithmInfo PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类AlgorithmInfo的操作映射成为对应的持久化操作
 * @author admin
 * @created 2018-10-29
 */
public interface AlgorithmInfoMapper {


    /**
     * @description 根据algo_code查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoCode
     * @return
     */
    @Select("select count(1) from tbl_algo_algorithm_info where algo_code = #{algoCode} ")
    int findByCode(@Param("algoCode") String algoCode);
    /**
     * @Description 根据algo_name查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoName
     * @return
     */
    @Select("select count(1) from tbl_algo_algorithm_info where algo_name = #{algoName} ")
    int findByAlgoName(@Param("algoName") String algoName);

    /**
     * @description 根据algo_code查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoCode
     * @return
     */
    @Select("select * from tbl_algo_algorithm_info where algo_code = #{algoCode} ")
    @Results(
            {
                    @Result(id=true,column="id",property="id"),
                    @Result(column="algo_name",property="algoName"),
                    @Result(column="algo_code",property="algoCode"),
                    @Result(column="creater",property="creater"),
                    @Result(column="descrip",property="descrip"),
                    @Result(column="create_time",property="createTime"),
                    @Result(column="register_type",property="registerType"),
                    @Result(column="submitee",property="submitee"),
                    @Result(column="submit_time",property="submitTime"),
                    @Result(column="algo_version",property="algoVersion"),

                    @Result(column="serv_namespace",property="servNamespace"),
                    @Result(column="serv_url_name",property="servUrlName"),
                    @Result(column="serv_func_descrip",property="servFuncDescrip"),
                    @Result(column="algo_file_name",property="algoFileName"),
                    @Result(column="algo_file_path",property="algoFilePath"),
                    @Result(column="algo_impl_type",property="algoImplType"),
                    @Result(column="algo_type",property="algoType"),
                    @Result(column="algo_wdsl_file",property="algoWdslFile"),
                    @Result(column="algo_prior_level",property="algoPriorLevel")
            }
    )
    AlgorithmInfo findByAlgoCode(@Param("algoCode") String algoCode);
    /**
     * @description 根据algo_name查询应用系统
     * @author lvbq
     * @created 2018-10-29 19:39
     * @param algoName
     * @return
     */
    @Select("select id,algo_name,algo_code,creater,descrip,create_time,register_type,submitee,submit_time,algo_version,serv_namespace,serv_url_name" +
            ",serv_func_descrip,(select wmsys.wm_concat(algo_file_name) from tbl_algo_algorithm_file where algo_id = tbl_algo_algorithm_info.id) as algo_file_name" +
            ",algo_file_path,algo_impl_type,algo_type,algo_wdsl_file,algo_prior_level from tbl_algo_algorithm_info where algo_name = #{algoName} ")
    @Results(
            {
                    @Result(id=true,column="id",property="id"),
                    @Result(column="algo_name",property="algoName"),
                    @Result(column="algo_code",property="algoCode"),
                    @Result(column="creater",property="creater"),
                    @Result(column="descrip",property="descrip"),
                    @Result(column="create_time",property="createTime"),
                    @Result(column="register_type",property="registerType"),
                    @Result(column="submitee",property="submitee"),
                    @Result(column="submit_time",property="submitTime"),
                    @Result(column="algo_version",property="algoVersion"),

                    @Result(column="serv_namespace",property="servNamespace"),
                    @Result(column="serv_url_name",property="servUrlName"),
                    @Result(column="serv_func_descrip",property="servFuncDescrip"),
                    @Result(column="algo_file_name",property="algoFileName"),
                    @Result(column="algo_file_path",property="algoFilePath"),
                    @Result(column="algo_impl_type",property="algoImplType"),
                    @Result(column="algo_type",property="algoType"),
                    @Result(column="algo_wdsl_file",property="algoWdslFile"),
                    @Result(column="algo_prior_level",property="algoPriorLevel")
            }
    )
    AlgorithmInfo findBeanByAlgoName(@Param("algoName") String algoName);

}
