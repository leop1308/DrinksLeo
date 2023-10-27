package com.drinksleo.controller;

import com.drinksleo.config.EnvironmentTestVariables;
import com.drinksleo.dao.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/ping")

public class PingController {

    @Autowired
    EnvironmentTestVariables env;

    @GetMapping("/{id}")
    public ResponseEntity<String> pingId(@PathVariable String id) {
        return ResponseEntity.ok("Ping: OK "+id);
    }
    @GetMapping()
    public ResponseEntity<String> ping() {

        System.out.println("HOST PRINT: "+env.host);
        return ResponseEntity.ok("Ping: OK\nHOST PRINT: "+env.host);
    }
}
