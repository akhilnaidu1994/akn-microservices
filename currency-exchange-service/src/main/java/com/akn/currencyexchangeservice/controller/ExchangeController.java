package com.akn.currencyexchangeservice.controller;

import com.akn.currencyexchangeservice.model.ExchangeValue;
import com.akn.currencyexchangeservice.repository.ExchangeValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ExchangeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to){
        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from,to);
        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        exchangeValue.setPort(port);
        logger.info("{}",exchangeValue);
        return exchangeValue;
    }

}
