package com.center.medical.messagepf.config;

import com.center.medical.message.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

    /**
     * 线上接收线下产生的同步数据-队列，接收线下系统产生的实时同步数据
     *
     * @return
     */
    @Bean
    public Queue lcSyncDataQueue() {
        return new Queue(MessageConstant.LC_SYNC_DATA_QUEUE, true);
    }

    /**
     * 线上接收线下产生的同步数据-交换机，接收线下系统产生的实时同步数据
     *
     * @return
     */
    @Bean
    public TopicExchange lcSyncDataTopicExchange() {
        return new TopicExchange(MessageConstant.LC_SYNC_DATA_TOPIC_EXCHANGE);
    }

    /**
     * 绑定线上接收线下同步到线上的实时数据-消息路由
     *
     * @param lcSyncDataQueue
     * @param lcSyncDataTopicExchange
     * @return
     */
    @Bean
    public Binding bindingLcSyncDataQueue(Queue lcSyncDataQueue, TopicExchange lcSyncDataTopicExchange) {
        return BindingBuilder.bind(lcSyncDataQueue).to(lcSyncDataTopicExchange).with(MessageConstant.LC_SYNC_DATA_ROUTING_KEY);
    }


    /**
     * 线上接收线下产生的同步数据-队列，接收线下系统产生的闲时同步数据
     *
     * @return
     */
    @Bean
    public Queue lcSyncDataDelayQueue() {
        return new Queue(MessageConstant.LC_SYNC_DATA_DELAY_QUEUE, true);
    }

    /**
     * 线上接收线下产生的同步数据-交换机，接收线下系统产生的闲时同步数据
     *
     * @return
     */
    @Bean
    public TopicExchange lcSyncDataDelayTopicExchange() {
        return new TopicExchange(MessageConstant.LC_SYNC_DATA_DELAY_TOPIC_EXCHANGE);
    }

    /**
     * 绑定线上接收线下同步到线上的闲时数据-消息路由
     *
     * @param lcSyncDataDelayQueue
     * @param lcSyncDataDelayTopicExchange
     * @return
     */
    @Bean
    public Binding bindingLcSyncDataDelayQueue(Queue lcSyncDataDelayQueue, TopicExchange lcSyncDataDelayTopicExchange) {
        return BindingBuilder.bind(lcSyncDataDelayQueue).to(lcSyncDataDelayTopicExchange).with(MessageConstant.LC_SYNC_DATA_DELAY_ROUTING_KEY);
    }


    /**
     * 接收线下请求-队列
     *
     * @return
     */
    @Bean
    public Queue lcRequestQueue() {
        return new Queue(MessageConstant.LC_REQUEST_QUEUE, true);
    }

    /**
     * 接收线下请求-交换机
     *
     * @return
     */
    @Bean
    public TopicExchange lcRequestTopicExchange() {
        return new TopicExchange(MessageConstant.LC_REQUEST_TOPIC_EXCHANGE);
    }

    /**
     * 绑定接收线下请求-消息路由
     *
     * @param lcRequestQueue
     * @param lcRequestTopicExchange
     * @return
     */
    @Bean
    public Binding bindingLcRequestQueue(Queue lcRequestQueue, TopicExchange lcRequestTopicExchange) {
        return BindingBuilder.bind(lcRequestQueue).to(lcRequestTopicExchange).with(MessageConstant.LC_REQUEST_ROUTING_KEY);
    }
}
