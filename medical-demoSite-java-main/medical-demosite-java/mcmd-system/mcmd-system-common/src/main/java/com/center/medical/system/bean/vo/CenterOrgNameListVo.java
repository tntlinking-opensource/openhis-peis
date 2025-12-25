package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CenterOrgNameListVo implements Serializable {

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "所属组织")
    private String centerorgname;

}
