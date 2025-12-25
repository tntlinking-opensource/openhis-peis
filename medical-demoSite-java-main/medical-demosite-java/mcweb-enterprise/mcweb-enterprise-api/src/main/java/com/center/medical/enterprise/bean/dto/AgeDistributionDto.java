package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 年龄分布 返回数据
 */
@Data
public class AgeDistributionDto implements Serializable {
    private static final long serialVersionUID = 1222886354802261118L;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "年龄")
    private String age;
}
