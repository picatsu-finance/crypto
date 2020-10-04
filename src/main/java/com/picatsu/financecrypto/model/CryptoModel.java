package com.picatsu.financecrypto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "crypto")
@Builder
@Getter
public class CryptoModel {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String slug;
    private String status;
    private Object platform;
}
