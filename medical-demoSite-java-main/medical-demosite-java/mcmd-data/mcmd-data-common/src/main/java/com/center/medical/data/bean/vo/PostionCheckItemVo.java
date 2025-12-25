package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取部位检查项目数据 返回数据
 */
@Data
public class PostionCheckItemVo implements Serializable {
    private static final long serialVersionUID = 4261717625824008774L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "devicetypename")
    private String devicetypename;

    @ApiModelProperty(value = "studypositionname")
    private String studypositionname;

    @ApiModelProperty(value = "checkitemnname")
    private String checkitemnname;

}
