package com.metro.bi.busi.common;

import com.jacob.com.ComThread;
import com.jacob.com.SafeArray;
import com.metro.bi.busi.controller.InvokeController;
import com.metro.bi.busi.entity.MethodParameterDef;
import com.metro.bi.util.PrimitiveType;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DllHelper {

    private static final Logger log = LoggerFactory.getLogger(InvokeController.class);
    private static final int PRIMITIVE_TYPE_BASE=1;
    private static final int PRIMITIVE_TYPE_ARRAY=2;
    private static final int PRIMITIVE_TYPE_MAP=3;
    private static final int PRIMITIVE_TYPE_OBJECT=4;

//        private final String DLL_NAME = "ClassLibrary1.Encrypt";

//        private ActiveXComponent dotnetCom = null;
//        private Dispatch dispatch = null;
//        private Variant var = null;

        public DllHelper() {
//            dotnetCom = new ActiveXComponent(DLL_NAME);
//            dispatch = dotnetCom.getObject();
        }

        public static Object invokeDllFromSharp(String nameSpace, String className, String methodName, List<MethodParameterDef> paramDef, Map<String,Object> params){

            ComThread.InitSTA();
            Object ret=null;
            for(String s:params.keySet()){
                log.info(s+":"+params.get(s).toString());
            }
            try{
                if(paramDef!=null && paramDef.size()>0){
//                    Object[] pa = new Object[paramDef.size()];
                    Variant[] dllParam=new Variant[paramDef.size()];
                    for(int i=0;i<paramDef.size();i++){
                        if(PRIMITIVE_TYPE_BASE==paramDef.get(i).getPrimitiveTypeFlag()){
                            dllParam[i]=castPrimitiveJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
                        }
                        if(PRIMITIVE_TYPE_ARRAY==paramDef.get(i).getPrimitiveTypeFlag()){
                            dllParam[i]=castCompoundJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
                        }
//                        pa[i]=dllParam[i];
//                        pa[i]=params.get(paramDef.get(i).getFormalParamName());
                    }
//                    ActiveXComponent component = new ActiveXComponent(nameSpace+"."+className);
////                    Variant result = Dispatch.call(component, methodName , dllParam);
                    ActiveXComponent component = new ActiveXComponent(nameSpace+"."+className);
                    Variant result=component.invoke(methodName,dllParam);
                    ret=result.toString();
                    log.info("invoke result:"+result.toString());
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                ComThread.Release();
            }
            return ret;
        }

    public static Object invokeDllFromSharp4Test(String nameSpace, String className, String methodName, List<MethodParameterDef> paramDef, Map<String,Object> params){

        ComThread.InitSTA();
        Object ret=null;
        try{
            if(paramDef!=null && paramDef.size()>0){
//                Object[] pa = new Object[paramDef.size()];
                Variant[] dllParam=new Variant[paramDef.size()];
                for(int i=0;i<paramDef.size();i++){
                        if(PRIMITIVE_TYPE_BASE==paramDef.get(i).getPrimitiveTypeFlag()){
                            dllParam[i]=castPrimitiveJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
                        }
                        if(PRIMITIVE_TYPE_ARRAY==paramDef.get(i).getPrimitiveTypeFlag()){
                            dllParam[i]=castCompoundJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
                        }
//                        pa[i]=dllParam[i];
//                    pa[i]=params.get(paramDef.get(i).getFormalParamName());

                }

                ActiveXComponent component = new ActiveXComponent(nameSpace+"."+className);
                Variant result=component.invoke(methodName,dllParam);

//                switch (paramDef.size()){
//                    case 0:
//                        result = Dispatch.call(component, methodName );
//                        break;
//                    case 1:
//                        result = Dispatch.call(component, methodName , dllParam[0] );
//                        break;
//                    case 2:
//                        result = Dispatch.call(component, methodName , dllParam[0] , dllParam[1] );
//                        break;
//                    case 3:
//                        result = Dispatch.call(component, methodName , dllParam[0] , dllParam[1] , dllParam[2] );
//                        break;
//                        default:break;
//
//                }
//                Variant result = Dispatch.call(component, methodName , dllParam[0]);
                ret=result.toString();
                log.info(result.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            ComThread.Release();
        }
        return ret;
    }

    public static Object invokeDllFromSharp2Test(String nameSpace, String className, String methodName, List<MethodParameterDef> paramDef, Map<String,Object> params){

        ComThread.InitSTA();
        Object ret=null;
        try{
            if(paramDef!=null && paramDef.size()>0){
                Object[] pa = new Object[paramDef.size()];
//                Variant[] dllParam=new Variant[paramDef.size()];
                for(int i=0;i<paramDef.size();i++){
//                    if(PRIMITIVE_TYPE_BASE==paramDef.get(i).getPrimitiveTypeFlag()){
//                        dllParam[i]=castPrimitiveJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
//                    }
//                    if(PRIMITIVE_TYPE_ARRAY==paramDef.get(i).getPrimitiveTypeFlag()){
//                        dllParam[i]=castCompoundJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
//                    }
//                        pa[i]=dllParam[i];
                    pa[i]=params.get(paramDef.get(i).getFormalParamName());
                }
                ActiveXComponent component = new ActiveXComponent(nameSpace+"."+className);
                Variant result = Dispatch.call(component, methodName , pa );


                ret=result.toString();
                log.info(result.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            ComThread.Release();
        }
        return ret;
    }

    /**
     *
     * parse array:数组支持除了boolean、char、byte之外的其它基础类型。包括String
     *
     * @param typeName
     * @param realParam
     * @return
     */
        private static Variant castCompoundJava2Csharp(String typeName,Object realParam){
            Variant ret=new Variant();
            try {
                String tmp=realParam.toString();
                String[] from=tmp.substring(1,tmp.length()-1).split(",");
                int fromLength=from.length;

                if(from.length>0){
//                    1-int,2-float,3-double,4-string,5-short,6-long,7-boolean,8-byte
                    switch (typeName){
                        case "5":
                            short[] toShort=new short[fromLength];
                            for(int i=0;i<fromLength;i++){
                                toShort[i]=Short.parseShort(from[i]);
                            }
                            SafeArray array1=new SafeArray( Variant.VariantShort, fromLength );
                            array1.fromShortArray(toShort);
                            ret.putSafeArrayRef(array1);
                            break;
                        case "1":
                            int[] toInt=new int[fromLength];
                            for(int i=0;i<fromLength;i++){
                                toInt[i]=Integer.parseInt(from[i]);
                            }
                            SafeArray array2 = new SafeArray(Variant.VariantInt,fromLength);
                            int a=20,b=40;
                            array2.setInt(0,toInt[0]);array2.setInt(1,toInt[1]);
//                            array2.fromIntArray(toInt);
                            //如果需要继续使用当前的数据，则使用putSafeArrayRef
                            //如果只是传值，则可以使用putSafeArray(safeArray)
                            ret.putSafeArray(array2);
                            break;
                        case "long":
                            long[] toLong=new long[fromLength];
                            for(int i=0;i<fromLength;i++){
                                toLong[i]=Long.parseLong(from[i]);
                            }
                            SafeArray array3=new SafeArray( Variant.VariantLongInt, fromLength );
                            array3.fromLongArray(toLong);
                            ret.putSafeArrayRef(array3);
                            break;
                        case "float":
                            float[] toFloat=new float[fromLength];
                            for(int i=0;i<fromLength;i++){
                                toFloat[i]=Float.parseFloat(from[i]);
                            }
                            SafeArray array4=new SafeArray( Variant.VariantFloat, fromLength );
                            array4.fromFloatArray(toFloat);
                            ret.putSafeArrayRef(array4);
                            break;
                        case "double":
                            double[] toDouble=new double[fromLength];
                            for(int i=0;i<fromLength;i++){
                                toDouble[i]=Double.parseDouble(from[i]);
                            }
                            SafeArray array5=new SafeArray( Variant.VariantDouble, fromLength );
                            array5.fromDoubleArray(toDouble);
                            ret.putSafeArrayRef(array5);
                            break;
                        case "string":
                            String[] toString=new String[fromLength];
                            SafeArray array6=new SafeArray( Variant.VariantString, fromLength );
//                            array = new SafeArray();
                            for(int i=0;i<fromLength;i++){
                                toString[i]=from[i];
//                                array.setString(i,from[i]);
                            }
//                            array.setString(0,);
//                            如果需要继续使用当前的数据，则使用putSafeArrayRef
//                            如果只是传值，则可以使用putSafeArray(safeArray)
                            array6.fromStringArray(toString);
                            ret.putSafeArray(array6);
                            break;
                        default:
                            String[] toStr=new String[fromLength];
                            for(int i=0;i<fromLength;i++){
                                toStr[i]=from[i];
                            }
                            SafeArray array7=new SafeArray( Variant.VariantString, fromLength );
                            array7.fromStringArray(toStr);
                            ret.putSafeArray(array7);
                            break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return ret;

        }

    /**
     * 支持8种基础类型(short,int,long,string,float,double,boolean,byte)和String类型
     *
     * @param typeName
     * @param realParam
     * @return
     */
        private static Variant castPrimitiveJava2Csharp(String typeName,Object realParam){
            Variant ret=new Variant();
            try {
                switch(typeName){
                    case "8"://8-byte
                        ret.putByteRef(Byte.parseByte(realParam.toString()));
                        break;
                    case "5"://5-short
                        ret.putShortRef(Short.parseShort(realParam.toString()));
                        break;
                    case "1"://1-int
                        ret.putIntRef(Integer.parseInt(realParam.toString()));
                        break;
                    case "6"://6-long
                        ret.putLongRef(Long.parseLong(realParam.toString()));
                        break;
                    case "2"://2-float
                        ret.putFloatRef(Float.parseFloat(realParam.toString()));
                        break;
                    case "3"://3-double
                        ret.putDoubleRef(Double.parseDouble(realParam.toString()));
                        break;
                    case "4"://4-string
                        ret.putStringRef(realParam.toString());
                        break;
                    case "7"://7-boolean
                        ret.putBooleanRef(Boolean.getBoolean(realParam.toString()));
                        break;
                        default:
                        ret.putStringRef(realParam.toString());
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return ret;
        }

        public void te(String nameSpace, String className, String methodName, List<MethodParameterDef> paramDef, Map<String,Object> params){
            try {
                ComThread.InitSTA();
                ActiveXComponent myCom = new ActiveXComponent("TestDll01.DateUtil");
                try{
                    if(paramDef!=null && paramDef.size()>0){
                        Object[] pa = new Object[paramDef.size()];
//                        Variant[] dllParam=new Variant[paramDef.size()];
                        for(int i=0;i<paramDef.size();i++){
//                            if(PRIMITIVE_TYPE_BASE==paramDef.get(i).getPrimitiveTypeFlag()){
//                                dllParam[i]=castPrimitiveJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
//                            }
//                            if(PRIMITIVE_TYPE_ARRAY==paramDef.get(i).getPrimitiveTypeFlag()){
//                                dllParam[i]=castPrimitiveJava2Csharp(paramDef.get(i).getFormalParamType(),params.get(paramDef.get(i).getFormalParamName()));
//                            }
//                            pa[i]=dllParam[i];
                            pa[i]=params.get(paramDef.get(i).getFormalParamName());
                        }
                        ActiveXComponent component = new ActiveXComponent(nameSpace+"."+className);
                        Variant result = Dispatch.call(component, methodName , pa);
                        log.info(result.toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    ComThread.Release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public void teGeo2Cit(){
        try {
            ComThread.InitSTA();
            ActiveXComponent component = new ActiveXComponent("GeoToCit.GeoToCit");
            //get MS level dispatch object
//                Dispatch myCom = new Dispatch("ClassLibrary1.Encrypt");
            Object[] ob=new Object[2];
            ob[1]="meishi";
            ob[0]="monster";
            //查找到名为newFile.txt的句柄
            Variant result = Dispatch.call(component, "GeoToCit" ,ob );

            //改变窗口title
//                Dispatch.call(myCom, "SetWindowText" , result , "newFileChange.txt");
            log.info(result.toString());
            //释放资源
            ComThread.Release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void teIntAdd(){
        try {
            ComThread.InitSTA();
            ActiveXComponent component = new ActiveXComponent("ClassLibrary4.Hotmail");
            //get MS level dispatch object
//                Dispatch myCom = new Dispatch("ClassLibrary1.Encrypt");
            Object[] ob=new Object[2];
            ob[1]="80total";
            ob[0]="4mail";
            //查找到名为newFile.txt的句柄
            Variant result = Dispatch.call(component, "sendMail" ,ob );
            log.info("sum:"+result.toString());
            //释放资源
            ComThread.Release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public static void main(String[] args) {
//            try {
//                System.out.println("library path:"+System.getProperty("java.library.path"));
//                DllHelper test = new DllHelper();
//                test.var = test.dispatch.call(test.dotnetCom, "AddInt");
//                int result=Dispatch.call(test.dispatch,"AddInt",new Variant("1234"),new Variant("1111")).getInt();
//                System.out.println("result:"+result);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            ActiveXComponent xl = new ActiveXComponent("Excel.Application");//Word.Application
//            Dispatch xlo = (Dispatch)(xl.getObject());
//            try {
//                System.out.println("version="+xl.getProperty("Version"));
//                System.out.println("version="+Dispatch.get(xlo, "Version"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                xl.invoke("Quit", new Variant[] {});
//            }
//            new DllHelper().teIntAdd();
            PrimitiveType[] allType = PrimitiveType.values();

            for (PrimitiveType aLight : allType) {

//                System.out.println("当前灯name：" + aLight.name());
//
//                System.out.println("当前灯ordinal：" + aLight.ordinal());

                System.out.println("当前灯ordinal：" + aLight.getName());

                System.out.println("当前灯ordinal：" + aLight.getIndex());

//                System.out.println("当前灯：" + aLight);

            }


        }

    /**
     * functionality:invoke local command from jvm
     *
     *     process.exitValue();
     *     process.getErrorStream();
     * 　　process.getInputStream();
     * 　　process.getOutputStream();
     *
     * @param filePath
     * @param fileName
     */

    public static void cmdInvoke(String filePath,String fileName){
        Process process=null;
        String cmd="\"C:\\Windows\\System32\\regsvr32.exe\"";
        try {
            process = Runtime.getRuntime().exec(cmd+" \""+filePath+"\""+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
