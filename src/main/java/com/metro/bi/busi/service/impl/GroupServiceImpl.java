package com.metro.bi.busi.service.impl;

import com.metro.bi.busi.service.GroupService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Component("groupService")
public class GroupServiceImpl implements GroupService {
}
