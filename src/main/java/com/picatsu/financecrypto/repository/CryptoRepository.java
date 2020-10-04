package com.picatsu.financecrypto.repository;

import com.picatsu.financecrypto.model.CryptoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CryptoRepository  extends MongoRepository<CryptoModel, String> {


    List<CryptoModel> findBySymbolContainsIgnoreCase(String val);
    List<CryptoModel> findByNameContainsIgnoreCase(String val);
}
