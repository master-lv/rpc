package com.metro.bi.util;

import java.util.UUID;


/**        
 * Title: 生成UUID as token/sid
 * @author:DELL
 * @Date:2018/10/30 13 55
 */      
public class CodecUtil {
	
	public static String createUUID(){
		return UUID.randomUUID().toString();
	}
}
