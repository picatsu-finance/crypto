package com.picatsu.financecrypto.config.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class JWTDecoder {

    private final static String PREFERRED_USERNAME = "preferred_username";
    private static Base64 base64Url = new Base64(true);


    public static Object retrieveUsername(String jwtToken) throws JsonProcessingException {

        return new ObjectMapper().readValue(
                new String(base64Url.decode(jwtToken.split("\\.")[1])), HashMap.class)
                .get(PREFERRED_USERNAME);


    }


    public static String testDecodeJWT(String jwtToken) throws JsonProcessingException {
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+body);
        Map<String,Object> result =
                new ObjectMapper().readValue(body, HashMap.class);
        for(String a : result.keySet()) {
            System.out.println("map ===> "+ a + "<==>" + result.get(a));
        }
        return body;
    }

}
