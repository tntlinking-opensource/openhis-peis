package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 合并档案参数
 */
@Data
public class SaveMergeParam implements Serializable {

    private static final long serialVersionUID = 470649755864006951L;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idcardno;

    @ApiModelProperty(value = "id")
    private String id;
}
