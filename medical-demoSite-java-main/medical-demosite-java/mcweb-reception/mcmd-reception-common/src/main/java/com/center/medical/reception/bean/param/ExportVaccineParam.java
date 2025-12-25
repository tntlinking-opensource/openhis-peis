package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出(疫苗费用) 参数
 */
@Data
public class ExportVaccineParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -6977457032441006633L;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "含统收，1含，其他不含")
    private Integer containTongShou;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer idExamtype;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer fUsecodehiden;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private String branchId;
}
