package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员管理-档案合并-编辑-保存
 */
@Data
public class ELSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 1735059041988990015L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "id")
    private String id;

}
