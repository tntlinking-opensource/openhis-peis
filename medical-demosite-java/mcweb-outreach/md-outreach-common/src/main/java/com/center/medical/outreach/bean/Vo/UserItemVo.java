package com.center.medical.outreach.bean.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取预约体检者收费项目数据
 */
@Data
public class UserItemVo implements Serializable {
    private static final long serialVersionUID = 5405680795263360018L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;
}
