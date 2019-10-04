package com.in28minutes.microservices.currencyconversionservice.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitConfigurationValue {
    private int minimum;
    private int maximum;
}
