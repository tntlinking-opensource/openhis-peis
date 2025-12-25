package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取订单列表
 */
@Data
public class PeipatientDataVo implements Serializable {
    private static final long serialVersionUID = 2696165444438524693L;

    @ApiModelProperty(value = "体检状态")
    private String tjzt;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "订单")
    private String dd;

    @ApiModelProperty(value = "套餐")
    private String tc;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "健康体检状态数字")
    private Integer jktjztNum;

    @ApiModelProperty(value = "职业体检状态数字")
    private Integer zytjztNum;

    @ApiModelProperty(value = "健康体检状态名称")
    private String jktjzt;

    @ApiModelProperty(value = "职业体检状态名称")
    private String zytjzt;
}
