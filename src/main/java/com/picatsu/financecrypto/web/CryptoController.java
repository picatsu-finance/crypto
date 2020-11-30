package com.picatsu.financecrypto.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.picatsu.financecrypto.model.CryptoModel;
import com.picatsu.financecrypto.model.CustomResponse;
import com.picatsu.financecrypto.model.dto.CryptoModelDto;
import com.picatsu.financecrypto.repository.CryptoRepository;
import com.picatsu.financecrypto.service.CryptoService;
import com.picatsu.financecrypto.utils.CustomFunctions;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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


    @GetMapping(value = "/price/{symbol}/{cryptoCode}")
    @Operation(summary = "get crypto price ")
    public JsonNode getByCodeAndCrypto(@PathVariable String symbol, @PathVariable String cryptoCode )
            throws JsonProcessingException {
        log.info("/price/{symbol}/{cryptoCode} ");

        return  cryptoService.findPrice(symbol, cryptoCode);
    }


    @GetMapping(value = "/details/{symbol}/{market}")
    @Operation(summary = "getDetailsByCode crypto price by market ")
    public JsonNode getDetailsByCode(@PathVariable String symbol, @PathVariable String market )
            throws JsonProcessingException {
        log.info("getDetailsByCode for code ");

        return cryptoService.getDetailsByCode(symbol, market);
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
    @Deprecated
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

    @GetMapping(value = "/all")
    @Operation(summary = "get all crypto infos paginated ")
    public List<CryptoModel> getAll( HttpServletRequest request)  {

        customFunctions.displayStackTraceIP("/api/v1/crypto/all", request);
        return  this.cryptoRepository.findAll();
    }

    @GetMapping(value = "/search-crypto/{str}")
    @Operation(summary = "filter by caracter ")
    public List<CryptoModel> findCode(@PathVariable String str, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/crypto/search-crypto/{str}", request);

        return new ArrayList<>(
                Stream.of(this.cryptoRepository.findBySymbolContainsIgnoreCase(str),
                        this.cryptoRepository.findByNameContainsIgnoreCase(str))
                        .flatMap(List::stream)
                        .collect(Collectors.toMap(CryptoModel::getSymbol,
                                d -> d,
                                (CryptoModel x, CryptoModel y) -> x == null ? y : x))
                        .values());
    }


    @PostMapping(value = "/create")
    @Operation(summary = "add crypto model")
    public CryptoModelDto addCrypto(@RequestBody CryptoModel crypto, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/crypto/create", request);
        CryptoModel res =  this.cryptoRepository.insert(crypto);

        return CryptoModelDto.builder()
                .name(res.getName())
                .platform(res.getPlatform())
                .slug(res.getSlug())
                .status(res.getStatus())
                .symbol(res.getSymbol())
                .build();
    }


    @DeleteMapping(value= "/{crypto-code}")
    @Operation(summary = "delete crypto from db")
    public ResponseEntity<?> deleteCrypto(@PathVariable(value= "crypto-code") String code, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/crypto/{crypto-code}/delete", request);


        long val =  cryptoRepository.deleteAllBySymbol(code);

        if ( val == 1) {
            return new ResponseEntity<>(
                    "Deleted successfully ",
                    HttpStatus.OK);
        }
        if( val == 0 ) {
            return new ResponseEntity<>(
                    "Cannot find Symbol : " + code,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                "Obscure error ",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
