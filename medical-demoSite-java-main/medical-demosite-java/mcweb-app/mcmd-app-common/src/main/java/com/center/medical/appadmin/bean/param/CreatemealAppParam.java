package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序套餐分页参数
 */
@Data
public class CreatemealAppParam implements Serializable {
    private static final long serialVersionUID = 2414975207056922173L;


    @ApiModelProperty(value = "套餐名称")
    private String tcmc;


    @ApiModelProperty(value = "类型")
    private Integer type;


    @ApiModelProperty(value = "状态 -1下线, 0待处理 ,1上线 ")
    private Integer status;


}
