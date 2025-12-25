package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康检查职业禁忌证人员名单 参数
 */
@Data
public class SummaryDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 1300714088081945167L;

    @ApiModelProperty(value = "总结分类ID,1可疑职业病,2职业禁忌症,3复查")
    private Integer serialNo;

    @ApiModelProperty(value = "医院代码")
    private String cid;

    @ApiModelProperty(value = "团体名称")
    private String idOrg;

    @ApiModelProperty(value = "体检类别")
    private String medicaltype;

}
