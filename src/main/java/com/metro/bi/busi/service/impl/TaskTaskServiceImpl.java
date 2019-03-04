package com.metro.bi.busi.service.impl;


import com.metro.bi.busi.entity.TaskTask;
import com.metro.bi.busi.mapper.TaskTaskMapper;
import com.metro.bi.busi.service.TaskTaskService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("taskTaskService")
public class TaskTaskServiceImpl implements TaskTaskService {

    private TaskTaskMapper taskTaskMapper;

    public TaskTaskMapper getTaskTaskMapper() {
        return taskTaskMapper;
    }

    @Resource
    // 默认按名称(taskTaskMapper)注入,若名称匹配失败，则按照类型匹配
    public void setTaskTaskMapper(TaskTaskMapper taskTaskMapper) {
        this.taskTaskMapper = taskTaskMapper;
    }

    /**
     * @Description 新增
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param taskTask
     * @return int
     */
    public int add(TaskTask taskTask){
        return taskTaskMapper.insert(taskTask);
    }
    /**
     * @Description 更新
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param taskTask
     * @return int
     */
    public int update(TaskTask taskTask){
        return taskTaskMapper.update(taskTask);
    }
}
