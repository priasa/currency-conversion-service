package com.in28minutes.microservices.currencyconversionservice.controller;

import com.in28minutes.microservices.currencyconversionservice.entity.CurrencyConverterEntity;
import com.in28minutes.microservices.currencyconversionservice.entity.LimitConfigurationValue;
import com.in28minutes.microservices.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
import com.in28minutes.microservices.currencyconversionservice.proxy.LimitServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

//    @Autowired
//    LimitServiceProxy limitServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConverterEntity> convertCurrency(
            @PathVariable(name = "from") String from,
            @PathVariable(name = "to") String to,
            @PathVariable(name = "quantity") BigDecimal quantity) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("from", from);
        urlVariables.put("to", to);
        ResponseEntity<CurrencyConverterEntity> currencyExchangeResponse = new RestTemplate()
                .getForEntity("http://localhost:9100/currency-exchange/from/{from}/to/{to}",
                        CurrencyConverterEntity.class,
                        urlVariables);

        CurrencyConverterEntity currencyConverterEntity = currencyExchangeResponse.getBody();
        currencyConverterEntity.setQuantity(quantity);
        currencyConverterEntity.setTotalCalculatedAmount(quantity.multiply(currencyConverterEntity.getConversionMultiple()));
        return ResponseEntity.ok().body(currencyConverterEntity);
    }

    @GetMapping("/currency-converter-feign/limits")
    public ResponseEntity<LimitConfigurationValue> retrieveLimitConfigurationValue() {
        LimitConfigurationValue limitConfigurationValue = currencyExchangeServiceProxy.retrieveLimitConfiguration();
        return ResponseEntity.ok().body(limitConfigurationValue);
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConverterEntity> convertCurrencyFeign(
            @PathVariable(name = "from") String from,
            @PathVariable(name = "to") String to,
            @PathVariable(name = "quantity") BigDecimal quantity) {
//        LimitConfigurationValue limitConfigurationValue = currencyExchangeServiceProxy.retrieveLimitConfiguration();
//        LOGGER.info("limitConfigurationValue -> getMininimum : " + limitConfigurationValue.getMinimum());
//        LOGGER.info("limitConfigurationValue -> getMaximum : " + limitConfigurationValue.getMaximum());
//
//        if(limitConfigurationValue != null) {
//            if (quantity.intValue() >= limitConfigurationValue.getMinimum()
//                    && quantity.intValue() <= limitConfigurationValue.getMaximum()) {
//                CurrencyConverterEntity currencyConverterEntity = currencyExchangeServiceProxy.retrieveExchangeValue(
//                        from,
//                        to);
//                currencyConverterEntity.setQuantity(quantity);
//                currencyConverterEntity.setTotalCalculatedAmount(quantity.multiply(currencyConverterEntity.getConversionMultiple()));
//                return ResponseEntity.ok().body(currencyConverterEntity);
//            }
//        }
//        return ResponseEntity.ok().body(new CurrencyConverterEntity());
        CurrencyConverterEntity currencyConverterEntity = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        currencyConverterEntity.setQuantity(quantity);
        currencyConverterEntity.setTotalCalculatedAmount(quantity.multiply(currencyConverterEntity.getConversionMultiple()));
        return ResponseEntity.ok().body(currencyConverterEntity);
    }
}
