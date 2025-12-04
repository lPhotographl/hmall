package com.hmall.api.client;

import com.hmall.api.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Set;

@FeignClient(value = "cart-service", configuration = DefaultFeignConfig.class)
public interface cartClient {

    /**
     * 根据商品id删除购物车中的商品
     * @param ids 商品id
     */
    @DeleteMapping("/carts")
    public void removeByItemIds(@RequestParam("ids") Collection<Long> ids);
}
