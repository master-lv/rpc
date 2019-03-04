package com.metro.bi.busi.service;

import java.sql.SQLException;
import java.util.List;

public interface HelloService {
    String sayHello(String name);
    List<String> invokeStoredPro();
}
