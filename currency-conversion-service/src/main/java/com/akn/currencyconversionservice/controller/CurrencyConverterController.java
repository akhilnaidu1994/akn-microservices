package com.akn.currencyconversionservice.controller;

import com.akn.currencyconversionservice.model.CurrencyConverter;
import com.akn.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
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
public class CurrencyConverterController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy exchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverter convertCurrency(@PathVariable("from") String from,
                                             @PathVariable("to") String to,
                                             @PathVariable("quantity") BigDecimal quantity){

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);
        ResponseEntity<CurrencyConverter> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConverter.class,
                uriVariables);
        CurrencyConverter currencyConverter = responseEntity.getBody();

        return new CurrencyConverter(currencyConverter.getId(),
                from,
                to,
                currencyConverter.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConverter.getConversionMultiple()),
                currencyConverter.getPort());
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverter convertCurrencyFeign(@PathVariable("from") String from,
                                             @PathVariable("to") String to,
                                             @PathVariable("quantity") BigDecimal quantity){
        CurrencyConverter currencyConverter = exchangeServiceProxy.retrieveExchangeValue(from, to);
        CurrencyConverter converter = new CurrencyConverter(currencyConverter.getId(),
                from,
                to,
                currencyConverter.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConverter.getConversionMultiple()),
                currencyConverter.getPort());
        logger.info("{}", converter);
        return converter;
    }
}
