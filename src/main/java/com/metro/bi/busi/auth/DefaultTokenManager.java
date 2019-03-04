package com.metro.bi.busi.auth;

import com.metro.bi.util.CodecUtil;
import com.metro.bi.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**        
 * Title: TokenManager的默认实现    
 * Description: 管理 Token
 * @author:DELL
 * @Date:2018/10/30 13 55
 */      
public class DefaultTokenManager implements TokenManager {

	/** 将token存储到JVM内存(ConcurrentHashMap)中   */
	private static Map<String, String> tokenMap = new ConcurrentHashMap<String, String>();

	/** 
	 * @description 利用UUID创建Token(用户登录时，创建Token)
	 * @author:DELL
	 * @Date:2018/10/30 13 55
	 * @param accountName
	 * @return     
	 * @see com.metro.bi.busi.auth.TokenManager#createToken(String)
	 */
	public String createToken(String accountName) {
		String token = CodecUtil.createUUID();
		tokenMap.put(token, accountName);
		return token;
	}


	/**
	 * @description Token验证(用户登录验证)
	 * @author:DELL
	 * @Date:2018/10/30 13 55
	 * @param token
	 * @return
	 * @see com.metro.bi.busi.auth.TokenManager#checkToken(String)
	 */
	public boolean checkToken(String token) {
		return !StringUtil.isEmpty(token) && tokenMap.containsKey(token);
	}


	/**
	 * @description Token删除(用户登出时，删除Token)
	 * @author:DELL
	 * @Date:2018/10/30 13 55
	 * @param token
	 * @see com.metro.bi.busi.auth.TokenManager#deleteToken(String)
	 */
	public void deleteToken(String token) {
		tokenMap.remove(token);
	}
}
