package com.zhen.payment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhen.payment.entity.Payment;
import com.zhen.payment.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    private PaymentMapper paymentMapper;
    
    @GetMapping("/{id}")
    public Payment test(@PathVariable Long id) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getId, id);
        Payment payment = paymentMapper.selectOne(wrapper);
        return payment;
    }
    
}
