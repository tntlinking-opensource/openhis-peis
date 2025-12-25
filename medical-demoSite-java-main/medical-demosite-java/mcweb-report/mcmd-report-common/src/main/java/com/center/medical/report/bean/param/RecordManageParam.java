package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 对比报告分页参数
 */
@Data
public class RecordManageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3858816357279130857L;

    @ApiModelProperty(value = "名称")
    private String patientname;

    @ApiModelProperty(value = "输入码（没用到）")
    private String inputCode;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "limit参数1")
    private Long offset;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "limit参数2")
    private Long limit;
}
