package com.zhen.user.clients;

import com.zhen.user.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Feign调用远程接口类
 */
@FeignClient("paymentService")
public interface PaymentClient {
    
    @GetMapping("/payment/{id}")
    Payment getPayment(@PathVariable Long id);
    
}
