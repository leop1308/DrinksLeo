package com.drinksleo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Slf4j
public class PasswordUtil {
    public static String encoder(String senha){
        BCryptPasswordEncoder encoderSenha = new BCryptPasswordEncoder();
        String hashedPass = encoderSenha.encode(senha);
        log.info("Hash password: {}",hashedPass);
        return hashedPass;
    }
//    public static String encoder(String senha, String salt, String strong){
//
//        BCryptPasswordEncoder encoderSenha = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 12, );
//        return encoderSenha.encode(senha);
//    }

}
