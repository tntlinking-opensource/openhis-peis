package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
@Data
public class SelltargetParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 4683602700653792887L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "年份")
    private String listYear;



}
