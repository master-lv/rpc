package com.metro.bi.busi.service.impl;

import com.metro.bi.busi.service.HelloService;
import com.metro.bi.util.DbUtil;

import java.sql.SQLException;
import java.util.List;

public class HelloServiceImpl implements HelloService {

    public String sayHello(String name){
        return "Hello " + name;
    }

    public List<String> invokeStoredPro() {
        return DbUtil.getListFromStoredProcedure();
    }

}
