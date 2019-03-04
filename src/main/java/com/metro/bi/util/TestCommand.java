package com.metro.bi.util;

import java.io.IOException;

public class TestCommand {


    public static void main(String[] ar){
        Process process=null;
        try {process = Runtime.getRuntime().exec("C:\\Users\\DELL\\Downloads\\nircmd-x64\\nircmd.exe elevate C:/Windows/Microsoft.NET/Framework64/v4.0.30319/RegAsm.exe /tlb C:\\Users\\DELL\\source\\repos\\TestDll01\\TestDll01\\bin\\x64\\Debug\\TestDll01.dll");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e1){
            e1.printStackTrace();
        }
        int i = process.exitValue();
        if(i==0){
            System.out.println("执行完成。");
        }else{
            System.out.println("执行失败。");
        }
        process.destroy();  //destroy sub process
    }
}
