package com.center.medical.admin.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

/**
 * @author: 路飞
 * @date: 2023-05-31 16:12
 * @description: 生产者
 */
@Component
public class TestProducer {
    private String producerGroup = "testgroup";

    private String nameServerAddr = "XXX.XXX.XXX.XXX:9876"; //多节点逗号分隔

    private DefaultMQProducer producer;

    public TestProducer() {
        producer = new DefaultMQProducer(producerGroup);

        producer.setNamesrvAddr(nameServerAddr);
        start();
    }

    public DefaultMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 对象在用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }
}
