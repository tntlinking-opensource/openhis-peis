package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送管理-外送结果上传 保存表格数据
 */
@Data
public class SgGriddataDto implements Serializable {

    private static final long serialVersionUID = -3946408970617426174L;

    @ApiModelProperty(value = "状态，removed删除，modified修改,added添加")
    private String state;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String idFee;

    @ApiModelProperty(value = "检查项目")
    private String Check;

    @ApiModelProperty(value = "检查项目ID")
    private String idCheck;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "科室名称")
    private String ksName;

    @ApiModelProperty(value = "收费项目id")
    private String idCharge;

    @ApiModelProperty(value = "结果值")
    private String result;

    @ApiModelProperty(value = "结果值")
    private String resultHand;
}
