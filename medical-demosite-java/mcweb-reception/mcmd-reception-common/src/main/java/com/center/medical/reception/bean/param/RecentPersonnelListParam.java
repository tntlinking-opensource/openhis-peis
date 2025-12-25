package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取最近人员列表 参数
 */
@Data
public class RecentPersonnelListParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7501826513128071838L;


    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "是否包含未登记的：0否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "团体名称")
    private String orgName;
}
