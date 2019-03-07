package com.metro.bi.busi.controller;


import com.jcraft.jsch.SftpException;
import com.metro.bi.busi.auth.IgnoreSecurity;
import com.metro.bi.busi.common.CommandHelper;
import com.metro.bi.busi.common.DllHelper;
import com.metro.bi.busi.common.JarHelper;
import com.metro.bi.busi.common.StoredProcHelper;
import com.metro.bi.busi.entity.*;
import com.metro.bi.busi.response.Response;
import com.metro.bi.busi.service.*;
import com.metro.bi.util.*;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Title: 调用入口
 * Description: 负责分发
 * @author lvbq
 * @Date 2018/10/30 13 55
 */
@RestController
@RequestMapping("/invoke")
public class InvokeController {

    private static final Logger log = LoggerFactory.getLogger(InvokeController.class);
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private AlgorithmInfoService algorithmInfoService;
    @Autowired
    private MethodInfoService methodInfoService;
    @Autowired
    private MethodParameterDefService methodParameterDefService;
    @Autowired
    private LogAlgoDetailService logAlgoDetailService;
    @Autowired
    private TaskTaskService taskTaskService;


    /**
     *
     *
     * @param params
     * @param response
     *
     * @Description 应用系统调用，调用方法并返回结果
     *
     */
    @RequestMapping(value="/s0",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreSecurity
    public void mainLoop(@RequestParam("type") int type,@RequestParam("appCode") String appCode,@RequestParam("algorithmName") String algorithmName,
                         @RequestParam("methodName") String methodName,@RequestParam("params") String params, HttpServletResponse response) {
        try {
//            log.info("body:"+type+appCode+algorithmName+methodCode+params);
//            验证授权，管理平台默认用户admin，不需验证
            if(!"admin".equals(appCode)){
                Response response1 = validAuthWithName(type,appCode,algorithmName,methodName,params);
                if(response1.getCode()>0){
                    JsonUtil.toJson(response1,response);return;
                }
            }
//            获取参数
            AlgorithmInfo algorithmInfo = algorithmInfoService.findBeanByAlgoName(algorithmName);
            MethodInfo methodInfo = methodInfoService.findByMethodName(methodName);
            List<MethodParameterDef> defList = methodParameterDefService.findByName(algorithmName,methodName);
            List<MethodParameterDef> defInList = new ArrayList<MethodParameterDef>();
            List<MethodParameterDef> defOutList = new ArrayList<MethodParameterDef>();
            if(null!=defList && defList.size()>0){
                for(MethodParameterDef o:defList){
                    if(null != o.getFormalParamAttr() && o.getFormalParamAttr().length()>0){
                        if("in".equals(o.getFormalParamAttr())){
                            defInList.add(o);
                        }
                        if("out".equals(o.getFormalParamAttr())){
                            defOutList.add(o);
                        }
                    }
                }
            }
//            验证参数
            Response response2 = validParams(defInList,params);
            if(response2.getCode()>0){
                JsonUtil.toJson(response2,response);return;
            }
//            download file(dll) from ftp service
//            algorithmInfo.getAlgoFileName() 是以逗号间隔的文件名称组成的字符串

            if(5==type){
                if(null!=algorithmInfo.getAlgoFileName() && algorithmInfo.getAlgoFileName().trim().length()>0){
                    if(!downAlgoFile(algorithmInfo.getAlgoFilePath(),algorithmInfo.getAlgoFileName(),ConfigUtil.getString("jar_local_save_path"))){
                        JsonUtil.toJson(new Response(107, "下载Jar文件失败."),response);return;
                    }
                }else{
                    JsonUtil.toJson(new Response(137, "Jar文件不存在."),response);return;
                }
            }
            TaskTask task = new TaskTask();
            task.setTaskName("中心调度");
            task.setTaskCode("1632548");
            task.setTaskPriors("2");
            task.setTaskType("2");
            task.setCronExpression("");
            task.setServiceUrl("./rpc/invoke/s0");
            task.setServiceParams(params);
            task.setMethodCode(methodInfo.getMethodCode());
            task.setDescrip("中心调度任务：算法-"+algorithmName+",方法-"+methodName+".");
            taskTaskService.add(task);
            LogAlgoDetail logAlgoDetail=new LogAlgoDetail();
            logAlgoDetail.setAlgoId(algorithmInfo.getId());
            logAlgoDetail.setAppCode(appCode);
            logAlgoDetail.setMethodId(methodInfo.getId());
            logAlgoDetail.setBeginTime(DateUtil.parseDateToStr("yyyy-MM-dd hh:mm:ss"));
            logAlgoDetailService.add(logAlgoDetail);
            log.info("id:"+logAlgoDetail.getId());
            switch (type){
                case 1:
                    if(null!=algorithmInfo.getAlgoFileName()){
//                        调用dll的公共方法
                        try {
//                            注册成功后，调用dll之前，创建任务
                            Object ret = DllHelper.invokeDllFromSharp(algorithmInfo.getServNamespace(),algorithmInfo.getAlgoName(),methodName,defInList,JsonUtil.fromJson2Map(params));
                            logAlgoDetail.setEndTime(DateUtil.parseDateToStr("yyyy-MM-dd hh:mm:ss"));
                            logAlgoDetail.setCompleteStatus(null==ret?"0":"1");
                            logAlgoDetailService.update(logAlgoDetail);
                            JsonUtil.toJson(new Response(1, "dll方法调用完成.",ret),response);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsonUtil.toJson(new Response(305, "dll方法"+methodInfo.getMethodName()+"调用失败."),response);
                            return;
                        }
                    }
                    break;
                case 2:
                    try {
                        if(defOutList.size()>0){
                            MethodParameterDef outPara=defOutList.get(0);
                            if(StringUtil.isEmpty(outPara.getFormalParamParent())){
//                                没有父类，基础类型
                                int r=StoredProcHelper.getAmountFromStoredProcedure(methodName,defInList,defOutList,JsonUtil.fromJson2Map(params));
                                JsonUtil.toJson(new Response(1, "存储过程调用完成.",r),response);
                            }else{
//                                有父类，集合类型
                                List<Map<String,Object>> list=StoredProcHelper.getListFromStoredProcedure(methodName,defInList,defOutList,JsonUtil.fromJson2Map(params));
                                JsonUtil.toJson(new Response(1, "存储过程调用完成.",list),response);
                            }
                            logAlgoDetail.setEndTime(DateUtil.parseDateToStr("yyyy-MM-dd hh:mm:ss"));
                            logAlgoDetail.setCompleteStatus("1");
                            logAlgoDetailService.update(logAlgoDetail);
                        }

                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsonUtil.toJson(new Response(303, "存储过程调用失败."),response);
                        return;
                    }
                case 3:
//                    List<MethodParameterDef> orderedParam=new ArrayList<MethodParameterDef>();
                    MethodParameterDef tmp=null;
//                    if(defInList.size()>0){
////                        bubblesort
//                        for(int i=0;i<defInList.size()-1;i++){
//                            for(int j=0;j<defInList.size()-1-i;j++){
//                                if(defInList.get(j).getDisplayOrder()>defInList.get(j+1).getDisplayOrder()){
//                                    tmp=defInList.get(j);
//                                    defInList.add(j,defInList.get(j+1));
//                                    defInList.add(j+1,tmp);
//                                }
//                            }
//                        }
//                    }
                    CommandHelper.invoke(methodName,defInList,JsonUtil.fromJson2Map(params));
                    break;
                case 5:
                    JarHelper.invoke(algorithmInfo.getAlgoFileName(),JsonUtil.fromJson2Map(params));
                    break;
                default:break;
            }

        }catch (Exception e){
            e.printStackTrace();
            JsonUtil.toJson(new Response(10, "方法调用失败."),response);
        }
        JsonUtil.toJson(new Response(1,"方法调用完成."),response);
    }

    private boolean downAlgoFile(String algoFilePath,String algoFileName,String localFilePath){
        try {
            String[] fileNames = algoFileName.split(",");
            for(String fileName:fileNames){
                File file = new File(localFilePath+fileName);
                if (!file.exists() || 1==ConfigUtil.getInt("use_latest_flag")) {
                    if(!downloadFile(algoFilePath,fileName)){
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    @RequestMapping(value="/s0",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
//    @IgnoreSecurity
//    public void mainLoop(@RequestParam("type") int type,@RequestParam("appCode") String appCode,@RequestParam("algorithmName") String algorithmName,
//                         @RequestParam("methodName") String methodName,@RequestParam("params") String params, HttpServletResponse response) {
//        try {
////            log.info("body:"+type+appCode+algorithmName+methodCode+params);
////            验证授权
//            Response response1 = validAuthWithName(type,appCode,algorithmName,methodName,params);
//            if(response1.getCode()>0){
//                JsonUtil.toJson(response1,response);return;
//            }
////            获取参数
//            AlgorithmInfo algorithmInfo = algorithmInfoService.findBeanByAlgoName(algorithmName);
//            MethodInfo methodInfo = methodInfoService.findByMethodName(methodName);
//            List<MethodParameterDef> defList = methodParameterDefService.findByName(algorithmName,methodName);
//            List<MethodParameterDef> defInList = new ArrayList<MethodParameterDef>();
//            List<MethodParameterDef> defOutList = new ArrayList<MethodParameterDef>();
//            if(null!=defList && defList.size()>0){
//                for(MethodParameterDef o:defList){
//                    if(null != o.getFormalParamAttr() && o.getFormalParamAttr().length()>0){
//                        if("in".equals(o.getFormalParamAttr())){
//                            defInList.add(o);
//                        }
//                        if("out".equals(o.getFormalParamAttr())){
//                            defOutList.add(o);
//                        }
//                    }
//                }
//            }
////            验证参数
//            Response response2 = validParams(defInList,params);
//            if(response2.getCode()>0){
//                JsonUtil.toJson(response2,response);return;
//            }
////            download file(dll) from ftp service
//            if(1==type){
//                if(null!=algorithmInfo.getAlgoFileName() && algorithmInfo.getAlgoFileName().trim().length()>0){
//                    File file = new File(ConfigUtil.getString("sftp_local_save_path")+algorithmInfo.getAlgoFileName());
//                    if (!file.exists() || 1==ConfigUtil.getInt("use_latest_flag")) {
//                        if(!downloadFile(algorithmInfo.getAlgoFilePath(),algorithmInfo.getAlgoFileName())){
//                            JsonUtil.toJson(new Response(107, "下载dll文件("+algorithmInfo.getAlgoFileName()+")失败."),response);return;
//                        }
//                    }
//                }else{
//                    JsonUtil.toJson(new Response(137, "dll文件不存在."),response);return;
//                }
//            }
//            switch (type){
//                case 1:
////                    register dll
//                    if(null!=algorithmInfo.getAlgoFileName()){
////                        本地注册dll
//                        try {
//                            if(1==CommandHelper.invokeRegister(ConfigUtil.getString("sftp_local_save_path"),algorithmInfo.getAlgoFileName())){
//                                JsonUtil.toJson(new Response(303, "DLL注册失败.RegAsm执行异常."),response);return;
//                            }
//                            Thread.sleep(1000l);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            JsonUtil.toJson(new Response(303, "dll"+algorithmInfo.getAlgoFileName()+"DLL注册失败."),response);
//                            return;
//                        }
////                        调用dll的公共方法
//                        try {
//                            Object ret = DllHelper.invokeDllFromSharp(algorithmInfo.getServNamespace(),algorithmInfo.getAlgoName(),methodName,defInList,JsonUtil.fromJson2Map(params));
//                            JsonUtil.toJson(new Response(1, "dll方法调用完成.",ret),response);
//                            return;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            JsonUtil.toJson(new Response(305, "dll方法"+methodInfo.getMethodName()+"调用失败."),response);
//                            return;
//                        }
//                    }
//                    break;
//                case 2:
//                    try {
//                        List<Map<String,Object>> list=StoredProcHelper.getListFromStoredProcedure(methodName,defInList,defOutList,JsonUtil.fromJson2Map(params));
//                        JsonUtil.toJson(new Response(1, "存储过程调用完成.",list),response);
//                        return;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        JsonUtil.toJson(new Response(303, "存储过程调用失败."),response);
//                        return;
//                    }
//                case 3:
//                    CommandHelper.invoke(algorithmInfo.getAlgoName(),new String[]{});
//                default:break;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            JsonUtil.toJson(new Response(10, "方法调用失败."),response);
//        }
//        JsonUtil.toJson(new Response(1,"方法调用完成."),response);
//    }

    /**
     * @Description 验证入参及算法授权
     *
     * @param appCode
     * @param algorithmName
     * @param methodName
     * @return
     */
    private Response validAuthWithName(int type,String appCode,String algorithmName,String methodName,String params){
        if(type>3 || type<0){
            return new Response(100, "调用类型("+type+")不支持.调用类型取值范围（1，2，3）,1-dll,2-存储过程（oracle）,3-exe");
        }
        int flag1=applicationService.findByCode(appCode);
        if(flag1==0){
            return new Response(101, "应用系统("+appCode+")未注册.");
        }
        int flag2=algorithmInfoService.findByAlgoName(algorithmName);
        if(flag2==0){
            return new Response(106, "算法("+ algorithmName +")未定义.");
        }
        int flag3=methodInfoService.findByName(algorithmName,methodName);
        if(flag3==0){
            return new Response(109, "算法("+ algorithmName +")中的方法("+ methodName +")未定义.");
        }
        int flag4=methodInfoService.validAppAuthWithName(appCode,algorithmName,methodName);
        if(flag4==0){
            return new Response(105, "应用系统("+ appCode +")无权限访问方法("+ methodName +").");
        }
        Map<String, Object> map = JsonUtil.fromJson2Map(params);
        if(map==null || map.size()==0){
            return new Response(112, "方法("+ methodName +")参数(params)解析异常.");
        }
        return new Response(0,"通过验证.");
    }

    /**
     *
     * @param params
     * @param response
     *
     * @Description 管理平台内部调用，验证方法可用性
     *
     */
    @RequestMapping(value="/innerTest",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreSecurity
    public void checkMethod4Test(@RequestParam("type") int type,@RequestParam("algorithmName") String algorithmName,
                            @RequestParam("methodName") String methodName,@RequestParam("params") String params, HttpServletResponse response) {
//        try {
//            if(1==CommandHelper.invokeRegister(ConfigUtil.getString("local_save_path"),"Geo4Cit.dll")){   //ClassLibrary8.dll
//                JsonUtil.toJson(new Response(303, "DLL注册失败.RegAsm执行异常."),response);return;
//            }
//            Thread.sleep(1000l);
//        } catch (Exception e) {
//            e.printStackTrace();
//            JsonUtil.toJson(new Response(303, "ClassLibrary8.dll注册失败."),response);
//            return;
//        }
//                        调用dll的公共方法
        try {

            List<MethodParameterDef> defInList=new ArrayList<MethodParameterDef>(8);
//            1-int,2-float,3-double,4-string,5-short,6-long,7-boolean,8-byte
            MethodParameterDef bean=new MethodParameterDef();
//            bean.setFormalParamName("lineCode");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(2);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("lineName");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("str");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("strKafka");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("str2");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("test");bean.setFormalParamType("boolean");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("kmBegin");bean.setFormalParamType("float");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("kmEnd");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("hours");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            Object ret = DllHelper.invokeDllFromSharp("ClassLibrary8",algorithmName,methodName,defInList,JsonUtil.fromJson2Map(params));

//            bean=new MethodParameterDef();bean.setFormalParamName("geoFilePath");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("lineCode");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("lineName");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("geo2CitMapRelationCsvFilePath");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("trainCode");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("runDir");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("backwardToForward");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("readMode");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            Object ret = DllHelper.invokeDllFromSharp("Geo4Cit",algorithmName,methodName,defInList,JsonUtil.fromJson2Map(params));

//            bean=new MethodParameterDef();bean.setFormalParamName("geoFilePath");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("kmBegin");bean.setFormalParamType("float");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("kmEnd");bean.setFormalParamType("float");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("readMode");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            Object ret = DllHelper.invokeDllFromSharp("GeoAdapter",algorithmName,methodName,defInList,JsonUtil.fromJson2Map(params));

            bean=new MethodParameterDef();bean.setFormalParamName("a");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
            bean=new MethodParameterDef();bean.setFormalParamName("b");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
            bean=new MethodParameterDef();bean.setFormalParamName("c");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
            Object ret = DllHelper.invokeDllFromSharp("TestDll01",algorithmName,methodName,defInList,JsonUtil.fromJson2Map(params));

            JsonUtil.toJson(new Response(1, "dll方法调用成功.",ret),response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.toJson(new Response(305, "dll方法"+methodName+"调用失败."),response);
            return;
        }
    }

    /**
     *
     * @param params
     * @param response
     *
     * @Description 管理平台内部调用，验证方法可用性
     *
     */
    @RequestMapping(value="/taskAdmin",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreSecurity
    public void taskSchedule(@RequestParam("type") int type,@RequestParam("algorithmName") String algorithmName,
                                 @RequestParam("methodName") String methodName,@RequestParam("params") String params, HttpServletResponse response) {
        try {
            if(1==CommandHelper.invokeRegister(ConfigUtil.getString("local_save_path"),"ClassLibrary8.dll")){
                JsonUtil.toJson(new Response(303, "DLL注册失败.RegAsm执行异常."),response);return;
            }
            Thread.sleep(1000l);
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.toJson(new Response(303, "ClassLibrary8.dll注册失败."),response);
            return;
        }
//                        调用dll的公共方法
        try {

            List<MethodParameterDef> defInList=new ArrayList<MethodParameterDef>(8);
            MethodParameterDef bean=new MethodParameterDef();bean.setFormalParamName("lineCode");bean.setFormalParamType("int");bean.setPrimitiveTypeFlag(2);defInList.add(bean);
            bean=new MethodParameterDef();bean.setFormalParamName("lineName");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("str2");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("test");bean.setFormalParamType("boolean");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("kmBegin");bean.setFormalParamType("float");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            bean=new MethodParameterDef();bean.setFormalParamName("kmEnd");bean.setFormalParamType("string");bean.setPrimitiveTypeFlag(1);defInList.add(bean);
//            Object ret = DllHelper.invokeDllFromSharp("ClassLibrary8",algorithmName,methodName,defInList,JsonUtil.fromJson2Map(params));
            Object ret = DllHelper.invokeDllFromSharp4Test("ClassLibrary8",algorithmName,methodName,defInList,JsonUtil.fromJson2Map(params));

            JsonUtil.toJson(new Response(1, "dll方法调用成功.",ret),response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.toJson(new Response(305, "dll方法"+methodName+"调用失败."),response);
            return;
        }
    }
    /**
     * @Description 验证入参及算法授权
     *
     * @param appCode
     * @param algorithmCode
     * @param methodCode
     * @return
     */
    private Response validAuth(int type,String appCode,String algorithmCode,String methodCode,String params){
        if(type>3 || type<0){
            return new Response(100, "调用类型("+type+")不支持.调用类型取值范围（1，2，3）,1-dll,2-存储过程（oracle）,3-exe");
        }
        int flag1=applicationService.findByCode(appCode);
        if(flag1==0){
            return new Response(101, "应用系统("+appCode+")未注册.");
        }
        int flag2=algorithmInfoService.findByCode(algorithmCode);
        if(flag2==0){
            return new Response(106, "算法("+ algorithmCode +")未定义.");
        }
        int flag3=methodInfoService.findByCode(algorithmCode,methodCode);
        if(flag3==0){
            return new Response(109, "算法("+ algorithmCode +")中的方法("+ methodCode +")未定义.");
        }
        int flag4=methodInfoService.validAppAuth(appCode,algorithmCode,methodCode);
        if(flag4==0){
            return new Response(105, "应用系统("+ appCode +")无权限访问方法("+ methodCode +").");
        }
        Map<String, Object> map = JsonUtil.fromJson2Map(params);
        if(map==null || map.size()==0){
            return new Response(112, "方法("+ methodCode +")参数(params)解析异常.");
        }
        return new Response(0,"通过验证.");
    }


    private Response validParams(List<MethodParameterDef> defInList,String params){
        Map<String, Object> map = JsonUtil.fromJson2Map(params);
        if(defInList==null || defInList.size()==0){
            return new Response(128, "不支持无参函数的调用.");
        }
        if(map.keySet().size()!=defInList.size()){
            return new Response(113, "形参与实参数量不匹配.");
        }
        return new Response(0,"通过验证.");
    }

    private boolean downloadFile(String remotefilePath,String fileName){
        try {
            SftpUtil sftp = new SftpUtil(ConfigUtil.getString("sftp_username"), ConfigUtil.getString("sftp_password"), ConfigUtil.getString("sftp_ip"), ConfigUtil.getInt("sftp_port"));
            sftp.login();
            sftp.download(remotefilePath,fileName,ConfigUtil.getString("sftp_local_save_path")+fileName);
            sftp.logout();
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     *
     * @param params
     * @param response
     *
     * @Description 管理平台内部调用，验证方法可用性
     *
     */
    @RequestMapping(value="/innerCheck",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreSecurity
    public void checkMethod(@RequestParam("type") int type,@RequestParam("algorithmName") String algorithmName,
                            @RequestParam("methodName") String methodName,@RequestParam("params") String params, HttpServletResponse response) {
        try {
//            log.info("body:"+params);
            Response response1 = validAuthByAdmin(type,algorithmName,methodName,params);
            if(response1.getCode()>0){
                JsonUtil.toJson(response1,response);return;
            }
            JsonUtil.toJson(new Response(1, "验证通过."),response);
//			更新反馈状态
//            bean.setAckId(smsResponse.getBizId());
//            bean.setSuccessFlag("OK".equals(smsResponse.getCode()) ? 1 : 0);
//            bean.setErrCode(smsResponse.getCode());
//            bean.setErrMsg("");
//			try {
//				Thread.sleep(1500L);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

//			smsHistoryService.updateSmsHistory(bean);
//            beanRet.setTelephone(bean.getTelephone());beanRet.setCheckCode(bean.getCheckCode());beanRet.setSuccessFlag(bean.getSuccessFlag());
        }catch (Exception e){
            e.printStackTrace();
            JsonUtil.toJson(new Response(10, "方法参数验证异常."),response);
        }
        JsonUtil.toJson(new Response(1,"验证通过方法可用."),response);
    }

    /**
     *
     * @param response
     *
     * @Description DLL注册测试
     *
     */
    @RequestMapping(value="/dllRegister",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreSecurity
    public void dllRegister(@RequestParam("type") int type,@RequestParam("algorithmName") String algorithmName,@RequestParam("fileName") String fileName,
                            HttpServletResponse response) {
        try {
//            本地注册dll
            try {
                CommandHelper.invokeRegister(ConfigUtil.getString("sftp_local_save_path"),fileName);
                Thread.sleep(1000l);
            } catch (Exception e) {
                e.printStackTrace();
                JsonUtil.toJson(new Response(303, "dll"+algorithmName+"DLL注册失败."),response);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
            JsonUtil.toJson(new Response(10, "方法参数验证异常."),response);
        }
        JsonUtil.toJson(new Response(1,"验证通过方法可用."),response);
    }

    private Response validAuthByAdmin(int type,String algorithmName,String methodName,String params){
        if(type>5 || type<0){
            return new Response(100, "调用类型("+type+")不支持.调用类型取值范围（1，2，3，4，5）,1-dll64,2-python,3-存储过程（oracle）,4-exe，5-jar");
        }
        int flag2=algorithmInfoService.findByAlgoName(algorithmName);
        if(flag2==0){
            return new Response(106, "算法("+ algorithmName +")未定义.");
        }
        int flag3=methodInfoService.findByName(algorithmName,methodName);
        if(flag3==0){
            return new Response(109, "算法("+ algorithmName +")中的方法("+ methodName +")未定义.");
        }
        Map<String, Object> map = JsonUtil.fromJson2Map(params);
        if(map==null || map.size()==0){
            return new Response(112, "方法("+ methodName +")参数(params)解析异常.");
        }
        if(type==1){
//            下载dll
            AlgorithmInfo algorithmInfo = algorithmInfoService.findBeanByAlgoName(algorithmName);
            if(1==type){
                if(null!=algorithmInfo.getAlgoFileName() && algorithmInfo.getAlgoFileName().trim().length()>0){
                    if(!downAlgoFile(algorithmInfo.getAlgoFilePath(),algorithmInfo.getAlgoFileName(),ConfigUtil.getString("sftp_local_save_path"))){
                        return new Response(107, "下载dll文件失败.");
                    }
                }else{
                    return new Response(137, "dll文件不存在.");
                }
            }
//            本地注册dll
            try {
                String[] fileNames = algorithmInfo.getAlgoFileName().split(",");
                for(String fileName:fileNames){
                    CommandHelper.invokeRegister(ConfigUtil.getString("sftp_local_save_path"),fileName);
                }
                Thread.sleep(1000l);
            } catch (Exception e) {
                e.printStackTrace();
                return new Response(303, "dll"+algorithmInfo.getAlgoFileName()+"DLL注册失败.");
            }
        }
        return new Response(0,"通过验证.");
    }

}


