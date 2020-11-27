package com.picatsu.financecrypto.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.picatsu.financecrypto.model.CryptoModel;
import com.picatsu.financecrypto.model.CustomResponse;
import com.picatsu.financecrypto.repository.CryptoRepository;
import com.picatsu.financecrypto.service.CryptoService;
import com.picatsu.financecrypto.utils.CustomFunctions;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequestMapping(value = "/api/v1/crypto")
@RestController("CryptoController")
@Slf4j
@CrossOrigin
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private CustomFunctions customFunctions;

    @Autowired
    private CryptoRepository cryptoRepository;


    @GetMapping(value = "/price/{symbol}/{forexCode}")
    @Operation(summary = "get crypto price ")
    public JsonNode getByCodeAndCrypto(@PathVariable String symbol, @PathVariable String forexCode )
            throws JsonProcessingException {
        log.info("/price/{symbol}/{forexCode} ");

        return  cryptoService.findPrice(symbol, forexCode);
    }


    @GetMapping(value = "/{symbol}/{market}")
    @Operation(summary = "get crypto price by market ")
    public JsonNode getByCode(@PathVariable String symbol, @PathVariable String market )
            throws JsonProcessingException {
        log.info("tim serie for code ");

        return cryptoService.find(symbol, market);
    }


    @GetMapping(value = "/reloadDB")
    @Operation(summary = "reload database from static sources")
    public int Load() {
        log.info("Loading all Crypto Currencies");

        return cryptoService.loadingDB();
    }


    @GetMapping(value = "/paginate")
    @Operation(summary = "get all crypto infos paginated ")
    public Page<CryptoModel> populate(@RequestParam int page, @RequestParam int size, HttpServletRequest request)  {

        customFunctions.displayStackTraceIP("/api/v1/crypto/paginate", request);
        return  this.cryptoRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/search-crypto/{str}")
    @Operation(summary = "filter by caracter ")
    public List<CryptoModel> findCode(@PathVariable String str, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/crypto/search-crypto/{str}", request);
        return Stream.concat(this.cryptoRepository.findBySymbolContainsIgnoreCase(str).stream(),
                this.cryptoRepository.findByNameContainsIgnoreCase(str).stream()
        ).collect(Collectors.toList());
    }

    @PostMapping(value = "/create")
    @Operation(summary = "add crypto model  ")
    public CryptoModel addCrypto(@RequestBody CryptoModel crypto, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/crypto/create", request);
        return this.cryptoRepository.insert(crypto);
    }

    @DeleteMapping(value= "/{crypto-code}/delete")
    @Operation(summary = "delete crypto from db")
    public Boolean deleteCrypto(@PathVariable(value= "crypto-code") String code, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/crypto/{crypto-code}/delete", request);
        try {
            cryptoRepository.deleteById(code);
            return cryptoRepository.existsById(code);
        } catch (Exception e) {
            return null;
        }
    }
}
