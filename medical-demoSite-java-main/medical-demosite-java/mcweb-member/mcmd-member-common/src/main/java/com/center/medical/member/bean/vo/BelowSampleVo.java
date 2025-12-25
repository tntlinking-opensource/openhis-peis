package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
//不合格样品回访展示页
public class BelowSampleVo implements Serializable {

    private static final long serialVersionUID = 1815567545851314193L;
    /**
     *  a.cou,。
     * 	a.id,
     * 	a.patientcode,。
     * 	a.patientname,。
     * 	a.id_sex,。
     * 	a.dateregister,。
     * 	a.phone,。
     * 	a.visit_id,。
     * 	a.visit_time,。
     * 	a.memo,。
     * 	a.createdate,
     * 	a.note,。
     * 	a.again,。
     * 	a.id_examtype,
     * 	a.jktjzt,。
     * 	a.zytjzt。
     */

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "体检时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "体检时间")
    private Date dateregister;

    @Excel(name = "不合格样品数量")
    @ApiModelProperty(value = "不合格样品数量")
    private String count;

    @Excel(name = "不合格原因")
    @ApiModelProperty(value = "不合格原因")
    private String belowquestion;

    @ApiModelProperty(value = "再通知")
    private String noticeAgain;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String memo;

    @Excel(name = "回访人")
    @ApiModelProperty(value = "回访人")
    private String visitId;

    @Excel(name = "回访时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @Excel(name = "回访备注")
    @ApiModelProperty(value = "回访备注")
    private String note;


    @ApiModelProperty(value = "健康体检状态")
    private String jktjzt;


    @ApiModelProperty(value = "职业体检状态")
    private String zytjzt;


    @ApiModelProperty(value = "是否全部已处理")
    private Integer allProcess;

}
