package com.metro.bi.sys;


import com.metro.bi.busi.service.HelloService;
import com.metro.bi.busi.service.impl.HelloServiceImpl;
//import com.metro.bi.busi.common.RpcClient;
import com.metro.bi.busi.common.Server;
//import com.metro.bi.busi.common.impl.RpcServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Test{

    public void sayHello(String name){
        System.out.println("Hello "+name+".");
    }

//    public static void main(String[] ar){
//        Test obj=new Test();
//        obj.sayHello("World");
//    }


    public static void main(String[] args) throws IOException {

//        HelloService service = RpcClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
//        System.out.println(service.sayHello("test"));
//        System.out.println(service.invokeStoredPro());
    }
}
