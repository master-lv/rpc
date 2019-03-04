package com.metro.bi.busi.service.impl;


import com.metro.bi.busi.mapper.ApplicationMapper;
import com.metro.bi.busi.service.ApplicationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationMapper applicationMapper;

    public ApplicationMapper getApplicationMapper() {
        return applicationMapper;
    }

    @Resource
    // 默认按名称(applicationMapper)注入,若名称匹配失败，则按照类型匹配
    public void setApplicationMapper(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    /**
     * @description 根据code查询指定应用
     * @author:DELL
     * @Date:2018/11/01 13:55
     * @param code
     * @return int
     */
    public int findByCode(String code){
        return applicationMapper.findByCode(code);
    }
}
