package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验样本-不合格样本分页参数
 */
@Data
public class VisitMainParam implements Serializable {

    private static final long serialVersionUID = 6652215221377964743L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;
}
