package com.zhen.payment.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SpringRabbitListener {
    
    //@RabbitListener(queues = "test.queue")
    //public void listenTest1Queue(String msg) throws InterruptedException {
    //    System.out.println("消费者1——————————消息队列监听到消息" + msg + LocalDateTime.now());
    //    Thread.sleep(20);
    //}
    //
    //@RabbitListener(queues = "test.queue")
    //public void listenTest2Queue(String msg) throws InterruptedException {
    //    System.out.println("消费者2消息队列监听到消息" + msg + LocalDateTime.now());
    //    Thread.sleep(200);
    //}
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(Map<String, Integer> map) {
        System.out.println("dir1--监听到消息：" + map);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(Map<String, Integer> map) {
        System.out.println("dir2--监听到消息：" + map);
    }
}
