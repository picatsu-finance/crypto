package com.picatsu.financecrypto.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Data
@Component
@AllArgsConstructor
public class CustomFunctions {


    public void displayStackTraceIP(String path, HttpServletRequest request ) {
        String ipAddress = request.getHeader("X-Forward-For");
        if(ipAddress== null){
            ipAddress = request.getRemoteAddr();
        }
        log.info("path = " + path + " IP : " +  ipAddress);
    }
}
