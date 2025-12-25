package com.center.medical.outreach.bean.Vo;

import lombok.Data;

/**
 * 第三方授权订单客户信息
 * @author: 路飞船长
 * @date: 2024/11/20 05:42
 * @description: 第三方授权订单客户信息
 */
@Data
public class CustomerVo {

    public CustomerVo(String id, String khdwmc, String khdwlxr, String khdh, String xsjl) {
        this.id = id;
        this.khdwmc = khdwmc;
        this.khdwlxr = khdwlxr;
        this.khdh = khdh;
        this.xsjl = xsjl;
    }

    /**
     * 客户ID
     */
    private String id;

    /**
     * 客户单位名称
     */
    private String khdwmc;

    /**
     * 客户单位联系人
     */
    private String khdwlxr;

    /**
     * 客户电话
     */
    private String khdh;

    /**
     * 销售经理
     */
    private String xsjl;


}
