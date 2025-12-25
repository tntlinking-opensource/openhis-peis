package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
//个检危险值回访展示页
public class PeispatientVo implements Serializable {
    private static final long serialVersionUID = -3313765927977733032L;
    /**
     * p.patientname,
     * p.id_sex,
     * p.patientcode,
     * p.age,
     * p.phone,
     * p.id_examtype,
     * p.doctorapply,
     * p.dateregister,
     * GROUP_CONCAT(c.gwxm ORDER BY c.gwxm SEPARATOR '、'),
     * GROUP_CONCAT(c.wjzxj ORDER BY c.gwxm),
     * min( c.wjzjb ),
     * u.user_name AS hfr,
     * str_to_date( f.visit_time, '%Y-%m-%d %T' ),
     * f.memo,
     * min( r.reportstatus )
     */

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String id;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "体检类型", readConverterExp = "0=健康,1=职业,2=综合")
    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "体检时间")
    @ApiModelProperty(value = "体检时间")
    private Date tjrq;

    @Excel(name = "阳性检查项目")
    @ApiModelProperty(value = "阳性检查项目")
    private String gwxm;

    @Excel(name = "阳性结果")
    @ApiModelProperty(value = "阳性结果")
    private String wjzxj;

    @Excel(name = "危险程度", readConverterExp = "1=高,2=中,3=低")
    @ApiModelProperty(value = "阳性结果")
    private String wjzjb;

    @Excel(name = "回访人")
    @ApiModelProperty(value = "回访人")
    private String visitId;

    @Excel(name = "回访时间")
    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @Excel(name = "回访备注")
    @ApiModelProperty(value = "回访备注")
    private String memo;

    @Excel(name = "处理状态", readConverterExp = "0=未回访,1=已回访")
    @ApiModelProperty(value = "处理状态，0=未回访,1=已回访")
    private String statuss;
}
