package com.center.medical.sellcrm.bean.vo;

import com.center.medical.bean.enums.ZYTJLB;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增套餐-体检套餐参数
 */
@Data
public class DataAddMealVo implements Serializable {
    private static final long serialVersionUID = -2985125326401257578L;

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlxid;

    @ApiModelProperty(value = "体检类型[0.健康体检 1.职业体检 2.综合 3.复查]")
    private Integer ftjlx;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "是否已婚套餐[0.是 1.否 其他.通用]")
    private String sfyhtc;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "适用性别")
    private String syxb;


    /**
     * @see ZYTJLB
     */
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String zytjlb;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    @ApiModelProperty(value = "分中心ID")
    private String fzxids;

    @ApiModelProperty(value = "分中心ID")
    private String fzx;

    @ApiModelProperty(value = "最小套餐标识：1.健康体检最小套餐 2.职业体检最小套餐")
    private String combostate;

    @ApiModelProperty(value = "数量")
    private String num;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步")
    private Integer tbzt;

    @ApiModelProperty(value = "是否推荐：0或null.否 1.是")
    private Integer isRecommended;

    @ApiModelProperty(value = "是否活动：0或null.否 1.是")
    private Integer isActive;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer isBan;

    @ApiModelProperty(value = "修改人用户名")
    private String modifier;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
