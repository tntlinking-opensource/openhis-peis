package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 样本类型 参数
 */
@Data
public class SpecimenTypeParam implements Serializable {
    private static final long serialVersionUID = 6334839413714632348L;

    @ApiModelProperty(value = "拼音码")
    private String pinyinCode;


}
