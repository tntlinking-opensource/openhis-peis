package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-23 17:20
 * @description: 体检报告待领提醒查询参数
 */
@Data
public class ReportRemindParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4895970146255665647L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "客户单位名称")
    private String orgName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "销售经理")
    private String xsjlid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否拥有决策管理者角色")
    private Boolean jueceManage;

}
