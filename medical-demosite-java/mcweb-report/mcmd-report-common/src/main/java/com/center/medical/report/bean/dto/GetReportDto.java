package com.center.medical.report.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * GetReport报告领取通知-健康报告领取通知
 */
@Data
public class GetReportDto implements Serializable {

    private static final long serialVersionUID = 3631966515753086377L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "体检者姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "体检状态：-2检验报告,-1报告上传时生成的,0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private Integer status;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "公司名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "发放方式")
    @ApiModelProperty(value = "发送方式名称")
    private String methodName;

    @Excel(name = "快递号")
    @ApiModelProperty(value = "快递号")
    private String expressNum;

    @Excel(name = "快递公司")
    @ApiModelProperty(value = "快递公司ID")
    private String expressId;

    @Excel(name = "通知时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "通知时间")
    private Date notifyDate;

    @Excel(name = "通知备注")
    @ApiModelProperty(value = "通知备注")
    private String notifyMemo;

    @Excel(name = "领取人")
    @ApiModelProperty(value = "报告领取人姓名（代领用，内部人领带出来）")
    private String getterName;

    @Excel(name = "领取时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @Excel(name = "领取人联系方式")
    @ApiModelProperty(value = "报告领取人电话")
    private String getterPhone;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "任务编号")
    private Double numorgresv;

    @Excel(name = "柜子号")
    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "发放人")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "交接人")
    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;

    @Excel(name = "交接时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "接受时间")
    private Date revTime;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;
}
