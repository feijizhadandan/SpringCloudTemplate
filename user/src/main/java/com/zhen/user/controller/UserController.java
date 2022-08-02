package com.zhen.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping
    public String test() {
        String json = restTemplate.getForObject("http://localhost:9001/payment", String.class);
        return json;
    }
    
}
