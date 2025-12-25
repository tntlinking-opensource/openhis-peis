package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 报告审核工作量分页参数
 */
@Data
public class ReportReviewParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -7218258399139843390L;

    @ApiModelProperty(value = "终审人")
    private String lastId;

    @ApiModelProperty(value = "体检类型")
    private String diseaseHealth;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;
}
