package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费信息查询 分页参数
 */
@Data
public class ChargeInfoParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5548275260804681671L;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "含统收")
    private Integer containTongShou;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "开单医师")
    private String doctorapply;
}
