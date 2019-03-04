package com.metro.bi.sys;

import com.metro.bi.busi.service.HelloService;
import com.metro.bi.busi.service.impl.HelloServiceImpl;
//import com.metro.bi.busi.common.RpcClient;
import com.metro.bi.busi.common.Server;
//import com.metro.bi.busi.common.impl.RpcServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerStarter {

    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            public void run() {
//                try {
//                    Server serviceServer = new RpcServer(8088);
//                    serviceServer.register(HelloService.class, HelloServiceImpl.class);
//                    serviceServer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

    }
}
