package com.metro.bi.util;

import java.util.Collection;


/**        
 * Title: Collection 工具类    
 * Description: 
 * @author:DELL
 * @Date:2018/10/30 13 55
 */      
public class CollectionUtil {

	public static boolean isEmpty(Collection<?> c){
		if (c == null || c.size() == 0 ) return true;
		return false;
	}
}
