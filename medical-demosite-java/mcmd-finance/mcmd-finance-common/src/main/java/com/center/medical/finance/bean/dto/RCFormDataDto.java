package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增充值保存左侧卡数据
 */
@Data
public class RCFormDataDto implements Serializable {
    private static final long serialVersionUID = -8813723922521504195L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "kyje")
    private String kyje;

    @ApiModelProperty(value = "卡标识")
    private String kbs;

    @ApiModelProperty(value = "有效期")
    private Date yxq;

    @ApiModelProperty(value = "卡说明")
    private String ksm;

    @ApiModelProperty(value = "卡备注")
    private String kbz;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分")
    private Double limit;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "检查人用户名")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String memotext;

}
