package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.AlgorithmInfo;
import com.metro.bi.busi.entity.LogAlgoDetail;
import org.apache.ibatis.annotations.*;


/**
 * Title: LogAlgoDetail PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类LogAlgoDetail的操作映射成为对应的持久化操作
 * @author admin
 * @created 2018-12-11
 */
public interface LogAlgoDetailMapper {

    /**
     * @Description  向数据库插入一条记录
     * @author admin
     * @Date  2018-11-30
     * @param logAlgoDetail
     */

    @Insert("insert into tbl_log_algo_detail(id, app_code, algo_id, method_id, begin_time) values (#{id},#{appCode},#{algoId},#{methodId},to_date(#{beginTime},'yyyy-mm-dd hh24:mi:ss')) ")
    @SelectKey(statement = "select seq_log_detail.nextval as id from dual", keyProperty = "id", before = true, resultType = int.class)
    int insert(LogAlgoDetail logAlgoDetail);


    /**
     * @Description 根据ID更新记录
     * @author admin
     * @Date  2018-11-30
     * @param logAlgoDetail
     */
    @Update("update tbl_log_algo_detail set complete_status=#{completeStatus},end_time=to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss'),elapsed_time=(to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss')-begin_time)*86400 where id=#{id}")
    int update(LogAlgoDetail logAlgoDetail);
}
