package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取复查项目
 */
@Data
public class RecheckItemsDto implements Serializable {
    private static final long serialVersionUID = -5735773473808829300L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "优惠价格")
    private String factprice;

    @ApiModelProperty(value = "科室")
    private String idKs;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "收费方式")
    private String idPayway;

    @ApiModelProperty(value = "是否加项")
    private String sfjx;

    @ApiModelProperty(value = "换项")
    private String changeItem;

    @ApiModelProperty(value = "弃检")
    private String FGiveup;

    @ApiModelProperty(value = "迟检")
    private String FDelayed;

    @ApiModelProperty(value = "是否是最小套餐：0不是 1是")
    private String isMintc;

    @ApiModelProperty(value = "是否备选：0不是 1是")
    private String isbx;

    @ApiModelProperty(value = "备选数量")
    private String bxcount;

}
