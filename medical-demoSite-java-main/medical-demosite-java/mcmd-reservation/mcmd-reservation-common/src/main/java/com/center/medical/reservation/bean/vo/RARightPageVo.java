package com.center.medical.reservation.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约来检情况分析 右侧页面返回数据
 */
@Data
public class RARightPageVo implements Serializable {
    private static final long serialVersionUID = 5598743840893539273L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "名称")
    private String patientname;

    @Excel(name = "客户类型" ,readConverterExp = "1=普通会员,1=vip,2=vvip")
    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @Excel(name = "预约时间段")
    @ApiModelProperty(value = "时间段")
    private String timePeriod;

    @Excel(name = "问卷状态" ,readConverterExp = "0=未答,1=已答")
    @ApiModelProperty(value = "问卷：1已答 0未答")
    private Integer questionnaire;

    @Excel(name="登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;


}
