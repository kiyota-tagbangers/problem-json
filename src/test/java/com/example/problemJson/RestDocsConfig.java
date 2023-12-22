package com.example.problemJson;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * @author kiyota
 */
@TestConfiguration(proxyBeanMethods = false)
public class RestDocsConfig {

    @Bean
    RestDocsMockMvcConfigurationCustomizer customizer() {
        return(configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()));
    }
}
