package com.metro.bi.util;

/**        
 * Title: 常量    
 * Description: 
 * @author DELL
 * @Date 2018/10/30 13:55
 */      
public class Constants {

	/**
	 * 存储当前登录账户id的字段名
	 */
	public static final String CURRENT_USER_ID = "uid";

	/**
	 * token有效期（分钟），一周内有效
	 */
	public static final int TOKEN_EXPIRES_HOUR = 60*24*7;

	/**  存放Token的header字段  */
	public static final String DEFAULT_TOKEN_NAME = "sid";

}
