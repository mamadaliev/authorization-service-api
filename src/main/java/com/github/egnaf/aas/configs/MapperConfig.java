package com.github.egnaf.aas.configs;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public DozerBeanMapper dozer() {
        return new DozerBeanMapper();
    }
}
