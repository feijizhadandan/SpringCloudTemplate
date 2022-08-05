package com.zhen;

import com.zhen.user.UserApplication;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = UserApplication.class)
class UserApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    public void test() throws InterruptedException {
        for(int i = 0; i < 50; i ++) {
            rabbitTemplate.convertAndSend("test.queue", "任务：" + i);
            Thread.sleep(20);
        }
    }
    
    @Test
    public void testDirect() {
        Map<String, Integer> map = new HashMap<>();
        map.put("age", 8);
        rabbitTemplate.convertAndSend("direct.exchange", "red", map);
    }

}
