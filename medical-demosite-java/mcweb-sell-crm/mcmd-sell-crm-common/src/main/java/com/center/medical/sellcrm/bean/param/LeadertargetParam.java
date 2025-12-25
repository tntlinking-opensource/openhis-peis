package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
@Data
public class LeadertargetParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -7301757310864357756L;


    @ApiModelProperty(value = "年份")
    private String listYear;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;


}
