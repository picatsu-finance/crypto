package com.picatsu.financecrypto;


import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@TestConfiguration
public class MockedMongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoMappingContext mappingContext = new MongoMappingContext();

        MongoConverter mongoConverter = Mockito.mock(MongoConverter.class);
        Mockito.when(mongoConverter.getMappingContext()).then(ignoredInvocation -> mappingContext);

        MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
        Mockito.when(mongoTemplate.getConverter()).thenReturn(mongoConverter);

        return mongoTemplate;
    }


}
