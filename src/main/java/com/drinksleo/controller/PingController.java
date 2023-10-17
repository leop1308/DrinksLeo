package com.drinksleo.controller;

import com.drinksleo.dao.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/ping")

public class PingController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getRecipe(@PathVariable String id) {
        return ResponseEntity.ok("Ping: OK "+id);
    }
    @GetMapping()
    public ResponseEntity<String> getRecipe() {
        return ResponseEntity.ok("Ping: OK");
    }
}
