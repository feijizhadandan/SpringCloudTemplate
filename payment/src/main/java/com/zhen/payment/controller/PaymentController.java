package com.zhen.payment.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhen.payment.entity.Payment;
import com.zhen.payment.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    private PaymentMapper paymentMapper;
    
    @Value("${pattern.dateformat}")
    private String dateformat;
    
    @GetMapping("/{id}")
    public Payment test(@PathVariable Long id) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getId, id);
        Payment payment = paymentMapper.selectOne(wrapper);
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat)));
        return payment;
    }
    
}
