package com.center.medical.machine.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告打印列表
 */
@Data
public class ReportPrintListVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "报告生成时间")
    private Date generateDate;

//    @ApiModelProperty(value = "健康报告pdf地址")
//    private String urlPdf;

    @ApiModelProperty(value = "打印次数,超过一次就要弹窗")
    private String printNum;

    @ApiModelProperty(value = "最后一次的打印时间")
    private String printTime;

}
