package com.hmall.api.fallback;

import com.hmall.api.client.itemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class itemClientFallback implements FallbackFactory<itemClient> {
    @Override
    public itemClient create(Throwable cause) {
        return new itemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.error("调用商品查询的列表失败；具体参数{}" , ids,cause);
                return Collections.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                //扣减商品库存的接口
                log.error("调用的商品库存失败，具体参数为 {}" ,items,cause);
                throw new RuntimeException("调用商品库存接口失败");
            }
        };
    }
}
