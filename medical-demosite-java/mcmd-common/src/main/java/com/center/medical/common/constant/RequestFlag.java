package com.center.medical.common.constant;

/**
 * @author: 路飞船长
 * @date: 2024/12/4 19:11
 * @description: 线上线下通信请求标识：1.推送体检者体检数据到第三方 2.从线上获取线下体检者数据 3.线上获取线下的银行流水数据
 */
public class RequestFlag {
    /**
     * 推送体检者体检数据到第三方
     */
    public static final int PUSH_DATA_TO_COO = 1;

    /**
     * 从线上获取线下体检者数据
     */
    public static final int SYNC_DATA_TO_ONL = 2;

    /**
     * 线上获取线下的银行流水数据
     */
    public static final int SYNC_RESER_TO_ONL = 3;

    /**
     * 线上推送博英心电图报告到线下
     */
    public static final int SENT_BOYING_ELE_RESULT_TO_OFFL = 4;
}
