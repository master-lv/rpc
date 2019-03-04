package com.metro.bi.busi.common;


import com.metro.bi.busi.controller.InvokeController;
import com.metro.bi.busi.entity.MethodParameterDef;
import com.metro.bi.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class CommandHelper {

    private static final Logger log = LoggerFactory.getLogger(CommandHelper.class);

    public static void invoke(String commandName, String[] param){
        Process process=null;
        StringBuffer commandLine=new StringBuffer();
        commandLine.append(commandName);
        if(null!= param && param.length>0){
            for(String s:param){
                commandLine.append(" " + s);
            }
        }
        String cmd=commandName;
        try {
            process = Runtime.getRuntime().exec(commandLine.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
//            process.getOutputStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void invoke(String commandName, List<MethodParameterDef> orderedParam, Map<String,Object> params){
        Process process=null;
        StringBuffer commandLine=new StringBuffer();
        commandLine.append(commandName);
        if(orderedParam.size()>0){
            for(int i=0;i<orderedParam.size();i++){
                commandLine.append(" " + params.get(orderedParam.get(i).getFormalParamName()));
            }
        }
//        commandLine.append(" " + params.get("source"));
//        commandLine.append(" " + params.get("destination"));
//        Object[] values=new Object[params.values().size()];
//        int i=0;
//        for(Object s:params.values()){
////            log.info(s+":"+params.get(s).toString());
//            values[i]=params.get(s);
//            i++;
////
//        }
//        for(int j=values.length-1;j<=0;j--){
//            commandLine.append(" " + values[j].toString());
//        }
        log.info(commandLine.toString());
        try {
            process = Runtime.getRuntime().exec(commandLine.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
            process.getOutputStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Description 在有指定环境和工作目录的独立进程中执行指定的字符串命令
     *
     * @param command
     * @param param
     * @param dir
     */
    public void invoke(String command, String[] param, File dir){
        Process process=null;
        try {
            process = Runtime.getRuntime().exec(command,param,dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
            process.getOutputStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = process.exitValue();
        if(i==0){
            log.info("执行完成。");
        }else{
            log.info("执行失败。");
        }
        process.destroy();  //destroy sub process
    }

    public static int invokeRegister(String filePath,String fileName){
        Process process=null;
        String cmdName= ConfigUtil.getString("regasm_full_path");
//        String cmdName= "C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\RegAsm.exe";
        try {
            log.info("file full path:"+ConfigUtil.getString("sftp_local_save_path")+fileName);
            process = Runtime.getRuntime().exec(cmdName+"  "+ConfigUtil.getString("sftp_local_save_path")+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
            printStream(process.getInputStream(),"getInputStream");
            printStream(process.getErrorStream(),"getErrorStream");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = process.exitValue();
        if(i==0){
            log.info("执行完成。");
        }else{
            log.info("执行失败。");
        }
        process.destroy();  //destroy sub process
        return i;

    }

    /**
     *
     * @param filePath
     * @param fileName
     */
    public static int invokeRegister2Assemble(String filePath,String fileName){
        Process process=null;
        String cmdName= ConfigUtil.getString("gacutil_full_path");
        try {
            process = Runtime.getRuntime().exec(cmdName+"  "+ConfigUtil.getString("sftp_local_save_path")+fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
            printStream(process.getInputStream(),"getInputStream");
            printStream(process.getErrorStream(),"getErrorStream");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e1){
            e1.printStackTrace();
        }
        int i = process.exitValue();
        if(i==0){
            log.info("执行完成。");
        }else{
            log.info("执行失败。");
        }
        process.destroy();  //destroy sub process
        return i;
    }

    private static void printStream(InputStream input, String title)
    {
        log.info("----------------"+title+"----------------");
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
//            isr = new InputStreamReader(input,ConfigUtil.getString("process_output_charset"));
            isr = new InputStreamReader(input,"GBK");
            br = new BufferedReader(isr);
            String str = null;
            while( (str = br.readLine()) != null)
            {
                log.info(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void invokeRegister4Test(){
        Process process=null;
        String cmdName= ConfigUtil.getString("regasm_full_path");
//        String cmdName= "C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\RegAsm.exe";
        try {
//            process = Runtime.getRuntime().exec(cmdName+" /I "+ConfigUtil.getString("sftp_local_save_path")+fileName);
//            process = Runtime.getRuntime().exec("cmd /C cmd.exe runAs /user:administrator C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\RegAsm.exe E:\\net\\vc#\\ClassLibrary1\\ClassLibrary1\\bin\\Debug\\ClassLibrary1.dll /tlb");
//            process = Runtime.getRuntime().exec("runas /savecred /env /user:DELL \"D:\\Program\\Notepad++\\notepad++.exe C:\\Users\\DELL\\Downloads\\cpauisrunase\\readme.txt\"");
//            process = Runtime.getRuntime().exec("runas /savecred /env /user:DELL C:/Windows/Microsoft.NET/Framework64/v4.0.30319/RegAsm.exe C:\\Users\\DELL\\source\\repos\\TestDll01\\TestDll01\\bin\\x64\\Debug\\TestDll01.dll");
//            process = Runtime.getRuntime().exec("E:\\bj\\project\\nircmd.exe elevate C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\RegAsm.exe E:\\net\\vc#\\ClassLibrary1\\ClassLibrary1\\bin\\Debug\\ClassLibrary1.dll /tlb");
//            process = Runtime.getRuntime().exec("cmd /C runAs /user:administrator netsh wlan set hostednetwork mode=allow ");
//            process = Runtime.getRuntime().exec("C:\\Users\\DELL\\Downloads\\nircmd-x64\\nircmd.exe elevate net start MySQL80");
//            process = Runtime.getRuntime().exec("E:/bj/project/nircmd.exe elevate net stop MySQL80");
//            process = Runtime.getRuntime().exec("E:/bj/project/nircmd.exe elevate C:/Windows/Microsoft.NET/Framework64/v4.0.30319/RegAsm.exe E:/net/vc#/ClassLibrary1/ClassLibrary1/bin/Debug/ClassLibrary1.dll /tlb");
//            process = Runtime.getRuntime().exec("E:/bj/project/nircmd.exe elevate ");

//            process = Runtime.getRuntime().exec("C:\\Users\\DELL\\Downloads\\nircmd-x64\\nircmd.exe elevate C:/Windows/Microsoft.NET/Framework64/v4.0.30319/RegAsm.exe /tlb C:\\Users\\DELL\\source\\repos\\TestDll01\\TestDll01\\bin\\x64\\Debug\\TestDll01.dll");

            process = Runtime.getRuntime().exec("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
            printStream(process.getInputStream(),"getInputStream");
            printStream(process.getErrorStream(),"getErrorStream");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = process.exitValue();
        if(i==0){
            log.info("执行完成。");
        }else{
            log.info("执行失败。");
        }
        process.destroy();  //destroy sub process
    }

    public static void main(String[] ar){
//        new CommandHelper().invoke("D:\\Program\\Notepad++\\notepad++.exe",ar);
//        invokeRegister("D:\\local\\dll\\","ClassLibrary1.dll");
        invokeRegister2Assemble("D:\\local\\dll\\","ClassLibrary1.dll");
//        try {
//            Runtime.getRuntime().exec("cmd /C cmd.exe runAs /user:administrator C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\RegAsm.exe E:\\net\\vc#\\ClassLibrary1\\ClassLibrary1\\bin\\Debug\\ClassLibrary1.dll");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
