package com.example.orderservice.controller;

import com.example.orderservice.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/token")
    public String generateToken(@RequestParam String username) {
        return JwtUtil.generateToken(username);
    }
}