package com.drinksleo.service;

import com.drinksleo.dao.User;
import com.drinksleo.dao.UserRepository;
import com.drinksleo.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) /*throws UsernameNotFoundException*/ {

        User user = userRepository.findByUsername(username);//.orElseThrow(() -> new UsernameNotFoundException("Not found"));
        log.info("Getting User:{} Senha: {}",user.getUsername(), user.getPassword());

        return user;
    }


    public User saveUser (User user) throws Exception {
        LocalDateTime time =  LocalDateTime.now();
        if(userRepository.existsByUsername(user.getUsername())){
            throw new Exception("That Recipe name already exists!");
        }

        DateTimeFormatter dateHourFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateHourFormat.format(time);
        user.setRegisterDate(currentTime);
        user.setLastAccess(currentTime);
        user.setPassword(PasswordUtil.encoder(user.getPassword()));
        log.info("New User Registered: Name= {} Time={}",user.getUsername(),currentTime);

        return userRepository.save(user);
    }



}
