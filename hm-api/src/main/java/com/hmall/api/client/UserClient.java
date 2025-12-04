package com.hmall.api.client;

import com.hmall.api.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
@FeignClient(value = "user-service", configuration = DefaultFeignConfig.class)
public interface UserClient {

    /**
     * 扣减用户余额
     * @param pw 支付密码
     * @param amount 金额
     */
    @PutMapping("users/money/deduct")
    public void deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount);
}
