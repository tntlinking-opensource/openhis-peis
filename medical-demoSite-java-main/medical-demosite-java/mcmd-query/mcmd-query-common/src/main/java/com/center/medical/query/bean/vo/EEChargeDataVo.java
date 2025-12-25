package com.center.medical.query.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 点击获取收费项目信息 返回数据
 */
@Data
public class EEChargeDataVo implements Serializable {
    private static final long serialVersionUID = -4365047069005187629L;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "已收费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "弃检")
    private Integer fGiveup;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;
}
