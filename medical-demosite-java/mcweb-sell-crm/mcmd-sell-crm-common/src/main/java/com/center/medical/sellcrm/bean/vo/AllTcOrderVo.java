package com.center.medical.sellcrm.bean.vo;

import com.center.medical.bean.enums.Fkfs;
import com.center.medical.bean.enums.ZYTJLB;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取订单下的所有套餐 返回数据
 */
@Data
public class AllTcOrderVo implements Serializable {
    private static final long serialVersionUID = -4654297001394716711L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检类型[0.健康体检 1.职业体检 2.综合 3.复查]")
    private Integer tjlx;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    /**
     * @see ZYTJLB
     */
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer zytjlb;

    @ApiModelProperty(value = "适用性别[0.男 1.女 其他.通用]")
    private Integer syxb;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "是否已备单")
    private String sfybd;

    @ApiModelProperty(value = "是否已婚套餐[0.是 1.否 其他.通用]")
    private String sfyhtc;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    /**
     * @see Fkfs
     */
    @ApiModelProperty(value = "付款方式：0.统收 1.自费")
    private String fkfs;

    @ApiModelProperty(value = "几选几")
    private String jxj;


    @ApiModelProperty(value = "普通套餐标示：0.普通套餐")
    private String combostate;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否自选套餐：0.固定套餐 1.完全自选 2.部分自选")
    private Integer sfzx;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer forbidden;

    @ApiModelProperty(value = "是否显示：0或者Null展示 1.隐藏")
    private Integer show;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "订单id")
    private String ddid;

    @ApiModelProperty(value = "接害因素名称")
    private String jhysn;
}
