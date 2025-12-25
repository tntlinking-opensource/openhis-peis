package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记保存表格数据
 */
@Data
public class PrGriddataDto implements Serializable {
    private static final long serialVersionUID = -7445581624002432668L;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "下载标志  0或NULL未下载   1已下载 2更新")
    private String FFeechargedinttrans;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "个数")
    private String count;

    @ApiModelProperty(value = "弃检 0 或者null：未弃检 1：弃检")
    private String FGiveup;


    @ApiModelProperty(value = "换项（相当于删除本项的标记）退项")
    private String changeItem;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private String FRegistered;

    @ApiModelProperty(value = "是否是最小套餐：0不是 1是")
    private String isMintc;

    @ApiModelProperty(value = "退费价格")
    private String endtuiprice;

    @ApiModelProperty(value = "迟检  1为迟捡")
    private String FDelayed;


    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "是否加项")
    private String sfjx;

    @ApiModelProperty(value = "登记员(冗余)")
    private String doctorregR;

    @ApiModelProperty(value = "备选数量")
    private String bxcount;


    @ApiModelProperty(value = "部位IDs")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "实际金额")
    private String actualprice;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;


    @ApiModelProperty(value = "_id")
    private int _id;

    @ApiModelProperty(value = "uid")
    private int uid;

    @ApiModelProperty(value = "状态,removed删除,modified修改,added添加")
    private String state;

    @ApiModelProperty(value = "退费拟退标志")
    private String fMarkFeereturn;



}
