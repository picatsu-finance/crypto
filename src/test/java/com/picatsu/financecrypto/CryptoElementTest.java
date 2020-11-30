package com.picatsu.financecrypto;


import com.picatsu.financecrypto.model.CryptoModel;
import com.picatsu.financecrypto.repository.CryptoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CryptoElementTest {

    @Autowired
    private CryptoRepository productRepo;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void searchProductTest() {
        // définition du comportement de la couche SGBD
        Assertions.assertThat(productRepo.findAll()).hasSize(0);
        CryptoModel product1 = productRepo.save(new CryptoModel("CODETEST", "CODETEST",
                "CODETEST", null, null, null));

        // appel de la couche Repository à tester
        Collection<CryptoModel> products = productRepo.findBySymbolContainsIgnoreCase("CODETEST");

        // vérification de la bonne exécution de la méthode find du MongoTemplate avec les bons paramètres
        Query query = new Query();
        query.addCriteria(Criteria.where("code").regex("CODETEST"));
        Mockito.verify(mongoTemplate, Mockito.times(1)).find(query, CryptoModel.class);

        // vérification du résultat de l'execution
        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).containsExactly(product1);
    }
}
