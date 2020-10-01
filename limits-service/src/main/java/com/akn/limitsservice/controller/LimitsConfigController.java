package com.akn.limitsservice.controller;

import com.akn.limitsservice.configuration.Properties;
import com.akn.limitsservice.model.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigController {

    @Autowired
    private Properties properties;

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfiguration(){
        return new LimitConfiguration(properties.getMaximum(), properties.getMinimum());
    }
}
