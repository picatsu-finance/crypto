package com.picatsu.financecrypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {
    private List<CryptoModel> data = new ArrayList<>();
    private Object status;




}


