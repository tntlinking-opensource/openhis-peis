package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 待领取报告分页查询参数
 */
@Data
public class ReportRemindParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2084820919579961957L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否领导 0否1是")
    private Integer isLeader;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;


}
