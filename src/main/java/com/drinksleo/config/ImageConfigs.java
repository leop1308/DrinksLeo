package com.drinksleo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "image.config.upload")
@Configuration("imageProperties")
public class ImageConfigs {
    private Boolean required = true;
    private String path;
}
