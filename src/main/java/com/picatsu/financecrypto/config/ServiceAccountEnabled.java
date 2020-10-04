package com.picatsu.financecrypto.config;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class ServiceAccountEnabled extends AllNestedConditions {

    ServiceAccountEnabled() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "rest.security", value = "enabled", havingValue = "true")
    static class SecurityEnabled {}

    @ConditionalOnProperty(prefix = "security.oauth2.client", value = "grant-type", havingValue = "client_credentials")
    static class ClientCredentialConfigurationExists {}

}
