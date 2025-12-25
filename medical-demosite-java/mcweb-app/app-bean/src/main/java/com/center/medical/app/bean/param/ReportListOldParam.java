package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取老系统列表数据参数
 */
@Data
public class ReportListOldParam implements Serializable {
    private static final long serialVersionUID = -236376583154458690L;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "身份证号,多个")
    private List<String> idcardno;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检")
    private String idExamtype;
}
