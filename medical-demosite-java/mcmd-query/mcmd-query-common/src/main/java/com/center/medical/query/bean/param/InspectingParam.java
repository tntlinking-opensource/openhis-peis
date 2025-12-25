package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 在检人员信息 分页 参数
 */
@Data
public class InspectingParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2688250294393769784L;


    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "体检者类型ID")
    private Integer idPatientclass;

    @ApiModelProperty(value = "体检类型id")
    private Integer idExamtype;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
