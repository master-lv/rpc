package com.metro.bi.busi.auth;

import com.metro.bi.busi.exception.TokenException;
import com.metro.bi.util.Constants;
import com.metro.bi.util.WebContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * Title:检查是否登录
 * Description: 通过验证Token维持登录状态
 *
 * @author:DELL
 * @Date:2018/10/30 13 55
 */
@Component
@Aspect
public class TokenSecurity {

    private static final Logger log = LoggerFactory.getLogger(TokenSecurity.class);

    private TokenManager tokenManager;

    @Resource(name = "tokenManager")
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        log.debug("methodSignature : " + methodSignature);
        Method method = methodSignature.getMethod();
        log.debug("Method : " + method.getName() + " : "
                + method.isAnnotationPresent(IgnoreSecurity.class));
        // 若目标方法忽略了安全性检查,则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return pjp.proceed();
        }

        // 从 request header 中获取当前 token
        String token = WebContextUtil.getRequest().getHeader(
                Constants.DEFAULT_TOKEN_NAME);
        // 检查 token 有效性
        if (!tokenManager.checkToken(token)) {
            String message = String.format("token [%s] is invalid", token);
            log.debug("message : " + message);
            throw new TokenException(message);
        }
        // 调用目标方法
        return pjp.proceed();
    }
}

