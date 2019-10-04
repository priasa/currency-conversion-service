package com.in28minutes.microservices.currencyconversionservice.proxy;

import com.in28minutes.microservices.currencyconversionservice.entity.LimitConfigurationValue;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient(name = "currency-limits-service")
//@FeignClient(contextId = "currencyLimitsServiceClient", name = "currency-zuul-gateway-server")
public interface LimitServiceProxy {

//    @GetMapping(name = "/limits")
//    @GetMapping(name = "/currency-limits-service/limits")
//    LimitConfigurationValue retrieveLimitConfiguration();
}
