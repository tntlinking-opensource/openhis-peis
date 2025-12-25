package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class SellDateParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 5523678945019590788L;

    @ApiModelProperty(value = "年-开始")
    private String startYear;

    @ApiModelProperty(value = "年-结束")
    private String endYear;

    @ApiModelProperty(value = "用户编号，不用传")
    private String userNo;
}
