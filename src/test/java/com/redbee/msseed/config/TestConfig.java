package com.redbee.msseed.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import com.redbee.msseed.config.properties.SpringConfigurationProperties;

import java.time.Duration;

@TestConfiguration
@ComponentScan("org.springframework.cloud.sleuth.autoconfig")
@Slf4j
@EnableAutoConfiguration
@EnableConfigurationProperties({SpringConfigurationProperties.class})
public class TestConfig {

    /**
     * Se utiliza prototype ya que para cada instancia de adapter requerimos tener un RestTemplate unico
     * debido a que el ErrorHandler utiliza uno especifica para cada adapter con sus propias excepciones.
     *
     * @param restTemplateBuilder Builder
     * @return RestTemplate
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                        @Value("${rest.client.default.timeout}") int timeout) {

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .build();
    }

}
