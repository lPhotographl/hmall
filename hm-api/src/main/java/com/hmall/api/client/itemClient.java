package com.hmall.api.client;


import com.hmall.api.config.DefaultFeignConfig;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Set;
@FeignClient(value = "item-service",
        configuration = DefaultFeignConfig.class)
public interface itemClient {

    /**
     * 根据商品id查询商品详情
     *
     * @param ids 商品id
     * @return 商品详情
     */
    @GetMapping("/items")
    List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);

    /**
     * 扣减商品库存
     *
     * @param items 商品列表
     */
    @PutMapping("/items/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items);
}
