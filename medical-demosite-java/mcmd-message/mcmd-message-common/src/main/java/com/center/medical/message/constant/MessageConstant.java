package com.center.medical.message.constant;

/**
 * 线下消息队列-常量
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
public class MessageConstant {
    // 线下数据同步消息队列配置名称
    /**
     * 线下数据同步队列，接收线下系统产生的实时同步数据
     */
    public static final String LC_SYNC_DATA_QUEUE = "lcSyncDataQueue";
    /**
     * 线下数据同步主题交换机，接收线下系统产生的同步数据
     */
    public static final String LC_SYNC_DATA_TOPIC_EXCHANGE = "lc.sync.data.topic";
    /**
     * 线下数据同步路由键，标识线下系统产生的同步数据
     */
    public static final String LC_SYNC_DATA_ROUTING_KEY = "lcSyncDataRoutingKey";


    // 线下数据闲时同步消息队列配置名称
    /**
     * 线下数据闲时同步队列，接收线下系统产生的闲时同步数据
     */
    public static final String LC_SYNC_DATA_DELAY_QUEUE = "lcSyncDataDelayQueue";
    /**
     * 线下数据闲时同步主题交换机，接收线下系统产生的闲时同步数据
     */
    public static final String LC_SYNC_DATA_DELAY_TOPIC_EXCHANGE = "lc.sync.data.delay.topic";
    /**
     * 线下数据闲时同步路由键，标识线下系统产生的闲时同步数据
     */
    public static final String LC_SYNC_DATA_DELAY_ROUTING_KEY = "lcSyncDataDelayRoutingKey";


    // 接收线下请求消息队列配置名称
    /**
     * 接收线下请求的队列
     */
    public static final String LC_REQUEST_QUEUE = "lcRequestQueue";
    /**
     * 接收线下请求的主题交换机
     */
    public static final String LC_REQUEST_TOPIC_EXCHANGE = "lc.request.topic";
    /**
     * 接收线下请求的路由键
     */
    public static final String LC_REQUEST_ROUTING_KEY = "lcRequestRoutßingKey";
}
