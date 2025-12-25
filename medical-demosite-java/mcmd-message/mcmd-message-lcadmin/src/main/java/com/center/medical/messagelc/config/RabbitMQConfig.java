package com.center.medical.messagelc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /**
     * 线上数据同步当前分中心队列，接收线上系统产生的当前分中心的同步数据
     */
    @Value("${sync.amq.offline.queue.data}")
    private String PF_SYNC_CD_QUEUE;
    /**
     * 线上数据同步当前分中心交换机，接收线上系统产生的当前分中心的同步数据
     */
    @Value("${sync.amq.offline.topicExchange.data}")
    private String PF_SYNC_CD_TOPIC_EXCHANGE;
    /**
     * 线上数据同步当前分中心路由键，标识线上系统产生的当前分中心同步数据
     */
    @Value("${sync.amq.offline.routingKey.data}")
    private String PF_SYNC_CD_ROUTING_KEY;

    /**
     * 线上文件同步当前分中心队列，接收线上系统产生的当前分中心的同步文件
     */
    @Value("${sync.amq.offline.queue.file1}")
    private String PF_SYNC_CF_QUEUE;
    /**
     * 线上文件同步当前分中心交换机，接收线上系统产生的当前分中心的同步文件
     */
    @Value("${sync.amq.offline.topicExchange.file1}")
    private String PF_SYNC_CF_TOPIC_EXCHANGE;
    /**
     * 线上文件同步当前分中心路由键，标识线上系统产生的当前分中心同步文件
     */
    @Value("${sync.amq.offline.routingKey.file1}")
    private String PF_SYNC_CF_ROUTING_KEY;

    /**
     * 当前分中心同步到线上的文件队列，接收当前分中心系统产生的同步文件
     */
    @Value("${sync.amq.offline.queue.file2}")
    private String LC_SYNC_CF_QUEUE;
    /**
     * 当前分中心同步到线上的文件交换机，接收当前分中心系统产生的同步文件
     */
    @Value("${sync.amq.offline.topicExchange.file2}")
    private String LC_SYNC_CF_TOPIC_EXCHANGE;
    /**
     * 当前分中心同步到线上的文件路由键，标识当前分中心系统产生的同步文件
     */
    @Value("${sync.amq.offline.routingKey.file2}")
    private String LC_SYNC_CF_ROUTING_KEY;

    /**
     * 接收线上向当前分中心发送请求的队列
     */
    @Value("${rabbitmq.offline.queue.request}")
    private String PTL_REQUEST_QUEUE;
    /**
     * 接收线上向当前分中心发送请求的交换机
     */
    @Value("${rabbitmq.offline.topicExchange.request}")
    private String PTL_REQUEST_TOPIC_EXCHANGE;
    /**
     * 接收线上向当前分中心发送请求的路由键
     */
    @Value("${rabbitmq.offline.routingKey.request}")
    private String PTL_REQUEST_ROUTING_KEY;


    /**
     * 线上产生的同步到当前分中心的数据消息队列
     *
     * @return
     */
    @Bean
    public Queue pfSyncCDQueue() {
        return new Queue(PF_SYNC_CD_QUEUE, true);
    }

    /**
     * 线上数据同步主题交换机，接收线上系统产生的同步数据
     *
     * @return
     */
    @Bean
    public TopicExchange pfSyncCDTopicExchange() {
        return new TopicExchange(PF_SYNC_CD_TOPIC_EXCHANGE);
    }

    /**
     * 绑定线上产生的同步到当前分中心的数据消息路由
     *
     * @param pfSyncCDQueue
     * @param pfSyncCDTopicExchange
     * @return
     */
    @Bean
    public Binding bindingSyncCFQueue(Queue pfSyncCDQueue, TopicExchange pfSyncCDTopicExchange) {
        return BindingBuilder.bind(pfSyncCDQueue).to(pfSyncCDTopicExchange).with(PF_SYNC_CD_ROUTING_KEY);
    }


    /**
     * 线上产生的同步到当前分中心的文件消息队列
     *
     * @return
     */
    @Bean
    public Queue pfSyncCFQueue() {
        return new Queue(PF_SYNC_CF_QUEUE, true);
    }

    /**
     * 线上数据同步主题交换机，接收线上系统产生的同步数据
     *
     * @return
     */
    @Bean
    public TopicExchange pfSyncCFTopicExchange() {
        return new TopicExchange(PF_SYNC_CF_TOPIC_EXCHANGE);
    }

    /**
     * 绑定线上产生的同步到当前分中心的文件消息路由
     *
     * @param pfSyncCFQueue
     * @param pfSyncCFTopicExchange
     * @return
     */
    @Bean
    public Binding bindingPfSyncCFQueue(Queue pfSyncCFQueue, TopicExchange pfSyncCFTopicExchange) {
        return BindingBuilder.bind(pfSyncCFQueue).to(pfSyncCFTopicExchange).with(PF_SYNC_CF_ROUTING_KEY);
    }


    /**
     * 线下文件同步到线上消息队列，接收线下系统产生的同步文件
     *
     * @return
     */
    @Bean
    public Queue lcSyncCFQueue() {
        return new Queue(LC_SYNC_CF_QUEUE, true);
    }

    /**
     * 线下文件同步到线上主题交换机，接收线下系统产生的同步文件
     *
     * @return
     */
    @Bean
    public TopicExchange lcSyncCFTopicExchange() {
        return new TopicExchange(LC_SYNC_CF_TOPIC_EXCHANGE);
    }

    /**
     * 绑定当前分中心的同步到线上的文件消息路由
     *
     * @param lcSyncCFQueue
     * @param lcSyncCFTopicExchange
     * @return
     */
    @Bean
    public Binding bindinglcSyncCFQueue(Queue lcSyncCFQueue, TopicExchange lcSyncCFTopicExchange) {
        return BindingBuilder.bind(lcSyncCFQueue).to(lcSyncCFTopicExchange).with(LC_SYNC_CF_ROUTING_KEY);
    }


    /**
     * 接收线上向当前分中心发送请求的队列
     *
     * @return
     */
    @Bean
    public Queue ptlRequestQueue() {
        return new Queue(PTL_REQUEST_QUEUE, true);
    }

    /**
     * 接收线上向当前分中心发送请求的交换机
     *
     * @return
     */
    @Bean
    public TopicExchange ptlRequestTopicExchange() {
        return new TopicExchange(PTL_REQUEST_TOPIC_EXCHANGE);
    }

    /**
     * 绑定线上向当前分中心发送请求的消息路由
     *
     * @param ptlRequestQueue
     * @param ptlRequestTopicExchange
     * @return
     */
    @Bean
    public Binding bindingPtlRequestQueue(Queue ptlRequestQueue, TopicExchange ptlRequestTopicExchange) {
        return BindingBuilder.bind(ptlRequestQueue).to(ptlRequestTopicExchange).with(PTL_REQUEST_ROUTING_KEY);
    }
}
