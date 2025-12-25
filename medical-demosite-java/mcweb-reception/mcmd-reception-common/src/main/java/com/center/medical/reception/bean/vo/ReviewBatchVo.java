package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业复查返回对象
 */
@Data
public class ReviewBatchVo implements Serializable {
    private static final long serialVersionUID = -7731562258878672779L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "价格")
    private String price;

}
