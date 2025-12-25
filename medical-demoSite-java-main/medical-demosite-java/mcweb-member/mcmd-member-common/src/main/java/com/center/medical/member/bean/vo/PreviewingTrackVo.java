package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个检预检回访分页返回数据
 */
@Data
public class PreviewingTrackVo implements Serializable {
    private static final long serialVersionUID = -3604420676331838567L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "是否来检" ,readConverterExp = "0=是,1=否,2=再通知")
    @ApiModelProperty(value = "是否来检：0是 1.否  2再通知 ")
    private Integer isInspect;


    @Excel(name = "备注")
    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "来检时间")
    private Date inspectTime;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女,2=通用")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "单位")
    private Integer dw;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "体检时间")
    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @Excel(name = "来检次数")
    @ApiModelProperty(value = "个数")
    private Integer count;

    @Excel(name = "体检项目")
    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @Excel(name = "体检结果")
    @ApiModelProperty(value = "结论")
    private String verdict;

    @Excel(name = "加项明细")
    @ApiModelProperty(value = "加项明细")
    private String additems;

    @ApiModelProperty(value = "地址")
    private String address;

    @Excel(name = "报告结论")
    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @Excel(name = "体检金额")
    @ApiModelProperty(value = "实付金额")
    private Double moneyamountpaid;

    @Excel(name = "支付方式")
    @ApiModelProperty(value = "支付方式")
    private String payways;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "回访人")
    @ApiModelProperty(value = "回访人ID")
    private String visitId;

    @Excel(name = "回访时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "回访时间")
    private Date visitTime;




}
