package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.LogAlgoDetail;
import com.metro.bi.busi.entity.TaskTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;


/**
 * Title: TaskTask PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类TaskTask的操作映射成为对应的持久化操作
 * @author admin
 * @created 2018-12-11
 */
public interface TaskTaskMapper {

    /**
     * @Description  向数据库插入一条记录
     * @author admin
     * @Date  2018-11-30
     * @param taskTask
     */

    @Insert("insert into tbl_task_task(id, task_name, task_code, task_priors, task_type, service_url, service_params, method_code, descrip) " +
            "values (#{id},#{taskName},#{taskCode},#{taskPriors},#{taskType},#{serviceUrl},#{serviceParams},#{methodCode},#{descrip}) ")
    @SelectKey(statement = "select seq_log_detail.nextval as id from dual", keyProperty = "id", before = true, resultType = int.class)
    int insert(TaskTask taskTask);


    /**
     * @Description 根据ID更新记录
     * @author admin
     * @Date  2018-11-30
     * @param taskTask
     */
    @Update("update tbl_task_task set elapsed_time=#{elapsedTime} where id=#{id}")
    int update(TaskTask taskTask);
}
