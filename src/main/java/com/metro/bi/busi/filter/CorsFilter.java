package com.metro.bi.busi.filter;

import com.metro.bi.util.CollectionUtil;
import com.metro.bi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**        
 * Title: 跨域访问处理(跨域资源共享)    
 * Description: 解决前后端分离架构中的跨域问题
 * @author:DELL
 * @Date:2018/10/31 13 55
 */      
public class CorsFilter implements Filter {

	/** Log4j日志处理(@author: rico) */
	private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);
	
	private String allowOrigin;
	private String allowMethods;
	private String allowCredentials;
	private String allowHeaders;
	private String exposeHeaders;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowOrigin = filterConfig.getInitParameter("allowOrigin");
		allowMethods = filterConfig.getInitParameter("allowMethods");
		allowCredentials = filterConfig.getInitParameter("allowCredentials");
		allowHeaders = filterConfig.getInitParameter("allowHeaders");
		exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
	}

	  
	/** 
	 * @description 通过CORS技术实现AJAX跨域访问,只要将CORS响应头写入response对象中即可
	 * @author:DELL
	 * @Date:2018/10/31 13 55
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException     
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */  
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String currentOrigin = request.getHeader("Origin");
		log.debug("currentOrigin : " + currentOrigin);
		if (!StringUtil.isEmpty(allowOrigin)) {
			List<String> allowOriginList = Arrays
					.asList(allowOrigin.split(","));
			log.debug("allowOriginList : " + allowOrigin);
			if (!CollectionUtil.isEmpty(allowOriginList)) {
				if (allowOriginList.contains(currentOrigin)) {
					response.setHeader("Access-Control-Allow-Origin",
							currentOrigin);
				}
			}
		}
		if (!StringUtil.isEmpty(allowMethods)) {
			response.setHeader("Access-Control-Allow-Methods", allowMethods);
		}
		if (!StringUtil.isEmpty(allowCredentials)) {
			response.setHeader("Access-Control-Allow-Credentials",
					allowCredentials);
		}
		if (!StringUtil.isEmpty(allowHeaders)) {
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		}
		if (!StringUtil.isEmpty(exposeHeaders)) {
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}