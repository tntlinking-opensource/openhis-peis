package com.center.medical.common.enums;

/**
 * 数据源
 *
 * @author 路飞
 */
public enum DataSourceType {
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库
     */
    SLAVE,

    /**
     * 沃德原系统Oracle数据库
     */
    OLDORACLE,

    /**
     * 虹桥lis
     */
    HONGQIAO,

    /**
     * 虹桥中间库
     */
    HONGQIAOSLAVE,

    /**
     * 瑞美lis
     */
    RUIMEI,

    /**
     * 瑞美中间库
     */
    RUIMEISLAVE,

    /**
     * 金卫lis
     */
    JINWEI,

    /**
     * 中核
     */
    ZHONGHE,

    /**
     * 小程序
     */
    APPLET,

    /**
     * 第二个中间库
     */
    SLAVE2,

    /**
     * 瑞林萨尔健康管理系统
     */
    RILIN,
}
