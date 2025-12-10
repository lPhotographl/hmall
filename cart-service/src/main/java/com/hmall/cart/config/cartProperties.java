package com.hmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@ConfigurationProperties(prefix = "hmall.cart")
@Component
@Data
public class cartProperties {

    private Integer maxAmount;

}
