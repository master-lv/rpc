package com.metro.bi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;


public class ConfigUtil{

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    private static Properties properties;
    private static boolean hasLoad = false;

    private ConfigUtil(){}

    private static Properties loadProperties(){
        properties = loadProperties("config.properties");
        hasLoad = true;
        return properties;
    }

    public static String getString(String key){
        if(!hasLoad)
            loadProperties();
        return properties.getProperty(key);
    }

    public static int getInt(String key){
        if(!hasLoad)
            loadProperties();
        return Integer.parseInt(properties.getProperty(key));
    }

    private static List getList(String key){
        if(!hasLoad)
            loadProperties();
        List list = new ArrayList();
        if(key == null || key.equals(""))
            return list;
        Enumeration propertyNames = properties.propertyNames();
        do{
            if(!propertyNames.hasMoreElements())
                break;
            String propertyName = (String)propertyNames.nextElement();
            if(propertyName != null && propertyName.indexOf(key) >= 0)
                list.add(properties.getProperty(propertyName));
        }while(true);
        return list;
    }

    /**
     * 加载配置文件
     */
    private static Properties loadProperties(String location){
        Properties properties = new Properties();
        try{
            InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(location);
            properties.load(in);
            in.close();
        }catch(IOException e){
            logger.error("加载配置文件错误",e);
        }catch(Exception e){
            logger.error("加载配置文件错误",e);
        }
        return properties;
    }

    public static void main(String[] ar){
        ConfigUtil.loadProperties();
        logger.info(ConfigUtil.getString("ftp_url"));
        logger.info(ConfigUtil.getString("local_save_path"));
    }

}
