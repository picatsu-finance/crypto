package com.picatsu.financecrypto.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picatsu.financecrypto.model.CryptoModel;
import com.picatsu.financecrypto.model.CustomResponse;
import com.picatsu.financecrypto.repository.CryptoRepository;
import com.picatsu.financecrypto.utils.CustomFunctions;
import lombok.Getter;
import org.patriques.AlphaVantageConnector;

import org.patriques.DigitalCurrencies;
import org.patriques.input.digitalcurrencies.Market;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.digitalcurrencies.IntraDay;
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Component
@Getter
public class CryptoService {

    private static final String BASE_URL = "https://www.alphavantage.co/query?";
    private static final String BASE_URL_YAHOO = "https://query1.finance.yahoo.com/v7/finance/quote?";
    
    @Autowired
    public AlphaVantageConnector apiConnector;

    @Autowired
    public CryptoRepository cryptoRepository;

    @Autowired
    private CustomFunctions customFunctions;


    public JsonNode findPrice(String symbol, String forex) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = BASE_URL_YAHOO+"&symbols="+symbol+"-"+forex+"&fields=regularMarketPrice,regularMarketChange," +
                "regularMarketChangePercent,regularMarketVolume";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root;


    }


    public JsonNode find(String symbol, String market) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = BASE_URL+"function=DIGITAL_CURRENCY_DAILY&symbol="+symbol+"&market="+market+"&apikey=63NJUA45A97BF6OI";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root;

    }


    public int loadingDB() {

        for(CryptoModel a : customFunctions.populateDbCrypto().getData()) {
            if(! this.cryptoRepository.existsById(a.getId()))
                this.cryptoRepository.insert(a);
        }

        return this.cryptoRepository.findAll().size();
    }

}
