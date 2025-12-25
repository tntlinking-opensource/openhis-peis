package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡搜索返回数据
 */
@Data
public class MedicalCardVo implements Serializable {
    private static final long serialVersionUID = -8388848891604277770L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "卡号")
    private String cardNo;


}
