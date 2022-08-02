package com.zhen.user.controller;

import com.zhen.user.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/{id}")
    public Payment test(@PathVariable Long id) {
        Payment payment = restTemplate.getForObject("http://paymentService/payment/" + id, Payment.class);
        return payment;
    }
    
}
