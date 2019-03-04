package com.metro.bi.busi.auth;

import java.lang.annotation.*;


/**        
 * Title:自定义注解     
 * @Description  标识是否忽略REST安全性检查
 * @author DELL
 * @Date 2018/10/30 13 55
 */      
@Target(ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
@Documented
public @interface IgnoreSecurity {

}
