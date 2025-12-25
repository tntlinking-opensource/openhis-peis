package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验数据导出参数
 */
@Data
public class InspectionExportParam extends BaseParam implements Serializable{
    private static final long serialVersionUID = -1415779703532392934L;

    @ApiModelProperty(value = "单位id")
    private String idOrg;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "项目id")
    private String examId;

    @ApiModelProperty(value = "检验开始时间")
    private Date startExamTime;

    @ApiModelProperty(value = "检验结束时间")
    private Date endExamTime;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
