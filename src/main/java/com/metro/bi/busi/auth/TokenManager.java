package com.metro.bi.busi.auth;

/**        
 * Title: 以token方式授权REST访问
 * Description: 登录账户的身份鉴权
 * @author admin
 * @created 2018-10-30
 */      
public interface TokenManager {

	String createToken(String accountId);

    boolean checkToken(String token); 
    
    void deleteToken(String token);
}
