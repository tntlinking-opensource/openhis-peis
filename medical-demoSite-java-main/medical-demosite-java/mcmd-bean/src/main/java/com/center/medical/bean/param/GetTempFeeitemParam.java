package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取科室加项数据 参数
 */
@Data
public class GetTempFeeitemParam implements Serializable {
    private static final long serialVersionUID = 8416719574196301058L;


    @ApiModelProperty(value = "体检号(团检预约需要)")
    private String patientcode;

    @ApiModelProperty(value = "是否只查询当前加项医师的；0否 1是")
    private Integer showAll;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "当前登录人用户编号（不用传）")
    private String userName;
}
