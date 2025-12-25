package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-17 16:36
 * @description: 团体列表数据
 */
@Data
public class POSimpleVo implements Serializable {
    private static final long serialVersionUID = 5870250155626438904L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "团体ID")
    private String khdwmcid;

    @ApiModelProperty(value = "分组ID")
    private String groupid;

    @ApiModelProperty(value = "分组名称")
    private String groupname;

    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @ApiModelProperty(value = "客户电话")
    private String khdh;

    @ApiModelProperty(value = "客户单位注册地址")
    private String address;

    @ApiModelProperty(value = "体检套餐ID")
    private String tcid;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "销售昵称")
    private String name;

    @ApiModelProperty(value = "客户添加时间")
    private Date date;

    @ApiModelProperty(value = "客户单位名称")
    private String orgName;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "套餐状态")
    private Integer combostate;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer sysex;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "订单号")
    private String num;

    @ApiModelProperty(value = "付款方式")
    private String fkfs;

    @ApiModelProperty(value = "销售员ID")
    private String saleId;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "销售电话")
    private String phone;

    @ApiModelProperty(value = "通知方式ID")
    private String idInforway;

    @ApiModelProperty(value = "检查类型，0.健康类 1.职业类 2.综合类 5.入职类 6.疫苗类 7.其他类")
    private Integer idExamclass;

}
