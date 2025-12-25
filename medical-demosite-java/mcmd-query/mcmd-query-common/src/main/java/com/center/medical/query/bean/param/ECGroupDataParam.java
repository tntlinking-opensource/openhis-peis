package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取分组数据 参数
 */
@Data
public class ECGroupDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8904740427737978992L;


    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "核酸科室")
    private List<String> itemIds;

    @ApiModelProperty(value = "标识")
    private Integer index;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

}
