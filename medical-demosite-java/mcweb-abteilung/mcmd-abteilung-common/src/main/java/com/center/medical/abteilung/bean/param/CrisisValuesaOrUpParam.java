package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 危机值提报添加或更新参数
 */
@Data
public class CrisisValuesaOrUpParam implements Serializable {
    private static final long serialVersionUID = -6069746711923070259L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "提报状态")
    private Integer reportstatus;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "销售经理（开单医生ID）")
    private String idOpendoctor;

    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "年龄")
    private String nl;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "危急值级别：高1、中2、3低")
    private String wjzjb;

    @ApiModelProperty(value = "体检日期")
    private Date tirq;

    @ApiModelProperty(value = "提报者")
    private String reportMan;

    @ApiModelProperty(value = "提报科室")
    private String reportDivision;

    @ApiModelProperty(value = "危机值小结")
    private String wjzxj;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlx;

}
