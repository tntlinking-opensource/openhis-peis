package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团检加/弃项保存表格数据
 */
@Data
public class GCSaOrUpDto implements Serializable {
    private static final long serialVersionUID = -8922367494510723524L;

    @ApiModelProperty(value = "性别")
    private String FFeechargedinttrans;

    @ApiModelProperty(value = "体检者收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "弃检 0 或者null：未弃检 1：弃检")
    private String FGiveup;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "换项")
    private Integer changeItem;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private String FRegistered;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "是否是最小套餐：0不是 1是")
    private Integer isMintc;

    @ApiModelProperty(value = "退费价格")
    private Double endtuiprice;

    @ApiModelProperty(value = "迟检  1为迟捡")
    private String FDelayed;

    @ApiModelProperty(value = "收费项目名称:必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;

    @ApiModelProperty(value = "FFeecharged")
    private String FFeecharged;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "FExaminated")
    private String FExaminated;

    @ApiModelProperty(value = "备选数量")
    private Integer bxcount;

    @ApiModelProperty(value = "序号")
    private Integer qty;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "实际金额")
    private Double actualprice;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "FMarkFeereturn")
    private String FMarkFeereturn;

    @ApiModelProperty(value = "序号")
    private String idPayway;

    @ApiModelProperty(value = "FLabsendtolis")
    private Integer FLabsendtolis;

    @ApiModelProperty(value = "_id")
    private Integer _id;

    @ApiModelProperty(value = "uid")
    private Integer uid;

    @ApiModelProperty(value = "状态 removed删除，modified修改，added添加")
    private String state;
}
