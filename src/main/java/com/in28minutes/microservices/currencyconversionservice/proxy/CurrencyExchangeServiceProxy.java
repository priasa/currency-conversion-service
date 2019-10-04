package com.in28minutes.microservices.currencyconversionservice.proxy;

import com.in28minutes.microservices.currencyconversionservice.entity.CurrencyConverterEntity;
import com.in28minutes.microservices.currencyconversionservice.entity.LimitConfigurationValue;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange-service")
//@RibbonClient(name = "currency-exchange-service")
@FeignClient(contextId = "currencyExchangeServiceClient", name = "currency-zuul-gateway-server")
public interface CurrencyExchangeServiceProxy {

//    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    CurrencyConverterEntity retrieveExchangeValue(@PathVariable("from") String from,
                                                  @PathVariable("to") String to);

    @GetMapping("/currency-limits-service/limits")
    LimitConfigurationValue retrieveLimitConfiguration();
}
