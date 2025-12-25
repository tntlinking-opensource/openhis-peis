package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-23 17:27
 * @description: 查询报告列表数据参数
 */
public class ReportParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 3964428878561111390L;
    
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "客户单位名称")
    private String orgName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否拥有决策管理者角色")
    private Boolean jueceManage;

}
