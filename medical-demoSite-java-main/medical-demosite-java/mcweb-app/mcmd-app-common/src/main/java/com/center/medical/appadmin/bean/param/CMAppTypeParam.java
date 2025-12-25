package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序套餐类型参数
 */
@Data
public class CMAppTypeParam implements Serializable {
    private static final long serialVersionUID = 5670970703636949802L;


    @ApiModelProperty(value = "名称")
    private String name;
}
