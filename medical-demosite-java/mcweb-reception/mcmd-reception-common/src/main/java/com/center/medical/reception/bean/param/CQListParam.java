package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费日报每日客服报表统计参数
 */
@Data
public class CQListParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2493215624658579819L;

    @ApiModelProperty(value = "含统收")
    private String containTongShou;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "客户单位名称id")
    private String khdwmcid;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}
