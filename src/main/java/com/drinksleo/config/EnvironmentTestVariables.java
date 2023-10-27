package com.drinksleo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration("envProperties")
public class EnvironmentTestVariables {
    @Value("${spring.data.mongodb.host}")
    public String host;
}
