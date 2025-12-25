package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告信息查询 分页 参数
 */
@Data
public class ReportQueryVo implements Serializable {
    private static final long serialVersionUID = 9130740652004056218L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @Excel(name = "登记时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;


    @Excel(name = "体检类型", readConverterExp = "0=健康,1=职业,2综合,3复查")
    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @Excel(name = "检查开始", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "已开始体检")
    private Integer fExamStart;

    @Excel(name = "分检完成", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @Excel(name = "总检锁定", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "总检锁定")
    private Integer fFinallocked;

    @Excel(name = "是否打印", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否已总检")
    private Integer isTotal;

    @Excel(name = "报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "体检状态")
    private String tjzt;

    @Excel(name = "总检医生")
    @ApiModelProperty(value = "总检医生")
    private String doctorFinalName;

    @Excel(name = "总检完成时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "总检时间")
    private Date datefinalexamed;

    @ApiModelProperty(value = "打印项目分类名称")
    private String printName;

    @ApiModelProperty(value = "打印时间")
    private Date printTime;

    @ApiModelProperty(value = "变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更")
    private Integer bgzt;

    @Excel(name = "交接人")
    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;

    @Excel(name = "交接时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "接受时间")
    private Date revTime;

    @Excel(name = "承接人")
    @ApiModelProperty(value = "接受人名称")
    private String revPersonMan;

    @ApiModelProperty(value = "体检状态")
    private String yfzt;

    @Excel(name = "通知时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "通知时间")
    private Date notifyDate;

    @Excel(name = "领取人")
    @ApiModelProperty(value = "报告领取人姓名（代领用，内部人领带出来）")
    private String getterName;

    @Excel(name = "领取时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @Excel(name = "领取人电话")
    @ApiModelProperty(value = "报告领取人电话")
    private String getterPhone;

    @Excel(name = "发放方式")
    @ApiModelProperty(value = "发送方式名称")
    private String sendMethod;

    @Excel(name = "快递号")
    @ApiModelProperty(value = "快递号")
    private String expressNum;

    @Excel(name = "快递公司")
    @ApiModelProperty(value = "快递公司ID")
    private String expressId;


    @ApiModelProperty(value = "体检状态")
    private String status;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "柜子号")
    @ApiModelProperty(value = "柜子号")
    private String chestNum;
}
