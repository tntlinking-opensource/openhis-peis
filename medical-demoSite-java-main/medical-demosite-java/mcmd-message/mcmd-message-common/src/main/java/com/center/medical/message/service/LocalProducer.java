package com.center.medical.message.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 线下消息队列-生产者
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
@Component
public class LocalProducer {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        //System.out.println("Local Message Sent: " + message);
    }
}
