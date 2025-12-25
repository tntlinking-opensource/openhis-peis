package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 校正体检者
 */
@Data
public class CheckPeispatientVo implements Serializable {
    private static final long serialVersionUID = 7123879622303970922L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "分组付款方式")
    private String idPaywayGroup;

}
