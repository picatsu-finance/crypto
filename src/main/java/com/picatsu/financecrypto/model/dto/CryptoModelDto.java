package com.picatsu.financecrypto.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class CryptoModelDto {

    private String name;
    private String symbol;
    private String slug;
    private String status;
    private Object platform;
}
