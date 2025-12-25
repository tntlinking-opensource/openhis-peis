package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 最近体检人员列表参数
 */
@Data
public class PaForReParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7303766920860812028L;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "档案号")
    private String idArchive;

    @ApiModelProperty(value = "健康报告状态")
    private String jkreportStatus;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "职业报告状态")
    private String zyreportStatus;

    @ApiModelProperty(value = "结案")
    private String close;

    @ApiModelProperty(value = "是否交单：0.否 1.是")
    private Integer countreportcompare;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "归档，0查询体检者表,其他查询体检者历史表，不传查体检者表的个数")
    private String dbtype;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "体检者表或体检者历史表,0或1")
    private Integer p;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "0或1")
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "0显示个数")
    private Integer flag;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;

}
