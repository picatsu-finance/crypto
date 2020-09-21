package com.picatsu.financecrypto.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.picatsu.financecrypto.service.CryptoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/api/v1/crypto")
@RestController("CryptoController")
@Slf4j
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;


    @GetMapping(value = "/{symbol}/{market}")
    public JsonNode getByCode(@PathVariable String symbol, @PathVariable String market ) throws JsonProcessingException {
        log.info("tim serie for code ");

        return cryptoService.find(symbol, market);
    }
}
