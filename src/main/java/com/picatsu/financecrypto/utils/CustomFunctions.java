package com.picatsu.financecrypto.utils;

import com.picatsu.financecrypto.model.CustomResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Data
@Component
@AllArgsConstructor
public class CustomFunctions {

    public CustomResponse populateDbCrypto() {
        RestTemplate res = new RestTemplate();
        String url= "https://web-api.coinmarketcap.com/v1/cryptocurrency/map?aux=status,platform&listing_status=active,untracked&sort=cmc_rank";

        return res.getForEntity (url, CustomResponse.class ).getBody();
    }

    public void displayStackTraceIP(String path, HttpServletRequest request ) {
        String ipAddress = request.getHeader("X-Forward-For");
        if(ipAddress== null){
            ipAddress = request.getRemoteAddr();
        }
        log.info("path = " + path + " IP : " +  ipAddress);
    }
}
