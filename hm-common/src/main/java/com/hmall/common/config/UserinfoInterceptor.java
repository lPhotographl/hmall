package com.hmall.common.config;
import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Configuration
@Slf4j
public class UserinfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 【修改这里】加上横杠，跟网关保持一致！
        String userInfo = request.getHeader("user-info");

        // 调试打印（保留是个好习惯）
        log.info("=================================================");
        log.info(">>>>> 拦截器启动！当前路径: {}", request.getRequestURI());
        log.info(">>>>> 获取到的 user-info 头信息: {}", userInfo);
        log.info("=================================================");

        // 2. 判空
        if (!StrUtil.isNotBlank(userInfo)) {
            // 这里建议不用直接 return false，否则前端收到的是 200 OK 空白页，很难排查。
            // 建议：直接放行（因为可能是 swagger 等不需要登录的接口），或者抛出 401 异常。
            // 但如果你确定 /carts 必须登录，这里 return false 会导致请求中断，前端没反应。
            // 咱们先保持现状，先让 ID 能拿到再说。
            return true; // 临时建议：先改成 true，防止因为拿不到 header 直接把请求吞了，方便看日志
        }

        // 3. 存入上下文
        UserContext.setUser(Long.parseLong(userInfo));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}