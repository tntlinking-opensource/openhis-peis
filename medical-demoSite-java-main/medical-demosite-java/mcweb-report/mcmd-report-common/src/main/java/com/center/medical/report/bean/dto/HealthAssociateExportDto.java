package com.center.medical.report.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HealthAssociateExportDto implements Serializable {
    private static final long serialVersionUID = -6054792543600078126L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "交接状态", readConverterExp = "-2=检验报告,-1=报告上传时生成的,0=总检开始,,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过,6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "体检状态：-2检验报告,-1报告上传时生成的,0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private Integer status;


    @Excel(name = "交接人")
    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;


    @Excel(name = "承接人")
    @ApiModelProperty(value = "接受人名称")
    private String revPersonMan;

    @Excel(name = "交接时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "接受时间")
    private Date revTime;

    @Excel(name = "发放方式")
    @ApiModelProperty(value = "发放方式")
    private String methodName;

    @Excel(name = "柜子号")
    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=通用")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Excel(name = "公司")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;


}
