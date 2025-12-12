package com.hmall.trade.listener;

import com.hmall.trade.service.IOrderService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PayStatus_Listener {

    @Autowired
    private IOrderService orderService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "pay.success", durable = "true"),
                    exchange = @Exchange(name = "pay.topic", type = ExchangeTypes.TOPIC),
                    key = "pay.success"
            )
    )
    public void listenPaySuccess(long bizOrderNo) {
        orderService.markOrderPaySuccess(bizOrderNo);
    }
}
