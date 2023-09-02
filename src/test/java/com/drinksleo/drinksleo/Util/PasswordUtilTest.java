package com.drinksleo.drinksleo.Util;

import com.drinksleo.util.PasswordUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordUtilTest {

    @Test
    public void testPassEnconder(){

        String result = PasswordUtil.encoder("myPassword");
        Assertions.assertNotEquals("myPassword", result);
    }
}
