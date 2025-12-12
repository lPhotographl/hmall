package com.hmall.gateway.fillter;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.common.utils.CollUtils;
import com.hmall.gateway.config.AuthProperties; // 下面会创建这个配置类
import com.hmall.gateway.utils.JwtTool;       // 引用 common 里的工具
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtTool jwtTool;
    private final AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取 Request
        ServerHttpRequest request = exchange.getRequest();

        // 2. 判断是否不需要拦截 (白名单放行)
        if (isExclude(request.getPath().toString())) {
            // 直接放行
            return chain.filter(exchange);
        }

        // 3. 获取 Token
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if (!CollUtils.isEmpty(headers)) {
            token = headers.get(0);

            // 【新增代码】处理 Bearer 前缀
            // 如果 Header 里的值是以 "Bearer " 开头（注意后面有个空格），就截取掉
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
        }

        // 4. 解析并校验 Token
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            log.warn("身份校验失败，拦截请求: {}，原因: {}", request.getPath(), e.getMessage());
            // 拦截：返回 401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 5. 传递用户信息 (将 userId 放入请求头，传给下游微服务)
        String userInfo = userId.toString();

        log.info("传递给下游服务的用户信息:{}",userInfo);

        ServerHttpRequest newRequest = request.mutate()
                .header("user-info", userInfo)
                .build();

        // 6. 放行
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    // 判断路径是否在白名单中
    private boolean isExclude(String path) {
        for (String pathPattern : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        // 优先级设高一点，保证在 Netty 路由之前执行
        return 0;
    }
}