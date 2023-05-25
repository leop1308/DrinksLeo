package com.drinksleo.drinksleo.config;

import com.drinksleo.config.ImageConfigs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ImageConfigs.class)
@TestPropertySource(locations ="/server-config-test.properties")
public class ImageConfigTest {

    @Autowired
    private ImageConfigs imageConfigs;

    @Test
    @DisplayName("Test Image Configurations")
    public void testConfigs(){

        assertEquals(false, imageConfigs.getRequired());
        assertEquals("C:\\Users\\PC\\OneDrive\\Estudo\\Programação\\Projetos\\drinksleo-front\\images\\drinks-images", imageConfigs.getPath());

    }

}
