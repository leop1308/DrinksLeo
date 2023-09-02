package com.drinksleo.controller;


import com.drinksleo.dao.User;
import com.drinksleo.service.UserService;
import com.drinksleo.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<UserDetails> getUser (@PathVariable String userName){
        return ResponseEntity.ok(userService.loadUserByUsername(userName));
    }

    @PostMapping(value="/new", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> saveUser (@RequestBody User user) throws Exception {

        return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
    }

}
