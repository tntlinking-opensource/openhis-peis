package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取体检者数据
 */
@Data
public class PatientForOrderIdVo implements Serializable {
    private static final long serialVersionUID = -6013282037204578012L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "工号")
    private String workno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "售卡人姓名")
    private String sellname;

    @ApiModelProperty(value = "订单号")
    private String ddh;
}
