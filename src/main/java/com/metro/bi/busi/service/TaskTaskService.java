package com.metro.bi.busi.service;

import com.metro.bi.busi.entity.LogAlgoDetail;
import com.metro.bi.busi.entity.TaskTask;

public interface TaskTaskService {

    /**
     * @Description 新增
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param taskTask
     * @return int
     */
    int add(TaskTask taskTask);
    /**
     * @Description 更新
     * @author :DELL
     * @Date 2018/11/01 13:55
     * @param taskTask
     * @return int
     */
    int update(TaskTask taskTask);
}
