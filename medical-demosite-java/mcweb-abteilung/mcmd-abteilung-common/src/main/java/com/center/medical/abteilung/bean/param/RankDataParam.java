package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息队列参数
 */
@Data
public class RankDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7608063500178096186L;

    @ApiModelProperty(value = "通过登录人姓名模糊查询,true或false")
    private String dependBy;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检状态，0未检1已检")
    private String status;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "体检类型，0健康1职业2综合")
    private String idExamtype;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "阳性结果")
    private Integer posistive;


    @ApiModelProperty(value = "姓名")
    private String patientname;
}
