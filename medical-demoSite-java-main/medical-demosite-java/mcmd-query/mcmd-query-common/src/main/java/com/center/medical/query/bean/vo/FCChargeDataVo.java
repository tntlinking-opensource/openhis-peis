package com.center.medical.query.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 点击获取收费项目信息 返回数据
 */
@Data
public class FCChargeDataVo implements Serializable {
    private static final long serialVersionUID = 3480107352155497345L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;
}
