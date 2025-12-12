package com.hmall.user.interceptor;

import com.hmall.common.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 直接从请求头拿网关贴好的“贴纸”
        String userInfo = request.getHeader("user-info");

        // 2. 检查有没有贴纸
        if (userInfo == null || userInfo.isBlank()) {
            // 没有贴纸，说明是非法请求或网关漏审，直接拦住
            response.setStatus(401);
            return false;
        }

        log.info("拿到用户信息user-info: {}", userInfo);

        // 3. 拿到用户ID，存入 ThreadLocal (UserContext)
        UserContext.setUser(Long.valueOf(userInfo));

        // 4. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 5. 任务结束，撕掉记录，防止内存泄漏
        UserContext.removeUser();
    }
}