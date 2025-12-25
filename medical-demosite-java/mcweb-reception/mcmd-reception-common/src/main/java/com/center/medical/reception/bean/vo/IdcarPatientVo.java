package com.center.medical.reception.bean.vo;

import com.center.medical.bean.enums.ExamType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-01 16:41
 * @description: 前台-登记管理-根据身份证号获取的体检者信息
 */
@Data
public class IdcarPatientVo implements Serializable {

    private static final long serialVersionUID = -5274146744754336054L;

    @ApiModelProperty(value = "体检者ID")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "体检团体")
    private String orgName;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

}
