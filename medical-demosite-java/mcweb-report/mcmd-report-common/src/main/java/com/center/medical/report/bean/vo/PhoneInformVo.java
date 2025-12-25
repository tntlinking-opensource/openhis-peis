package com.center.medical.report.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告领取通知-职业报告领取分页查询数据
 */
@Data
public class PhoneInformVo implements Serializable {

    private static final long serialVersionUID = -8090685551071368875L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "体检者姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女,2=通用")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "通知状态", readConverterExp = "-2=检验报告,-1=报告上传时生成的,0=总检开始,,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过,6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "体检状态：-2检验报告,-1报告上传时生成的,0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private Integer status;

    @Excel(name = "通知结果")
    @ApiModelProperty(value = "通知结果")
    private String notificationResult;

    @Excel(name = "柜子号")
    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @Excel(name = "发放方式")
    @ApiModelProperty(value = "发送方式名称")
    private String methodName;

    @Excel(name = "登记时间" ,dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "交接时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "接受时间")
    private Date revTime;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "快递号")
    private String expressNum;

    @ApiModelProperty(value = "快递公司ID")
    private String expressId;

    @ApiModelProperty(value = "报告领取人姓名（代领用，内部人领带出来）")
    private String getterName;

    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @ApiModelProperty(value = "报告领取人电话")
    private String getterPhone;

    @ApiModelProperty(value = "任务编号")
    private Double numorgresv;

    @ApiModelProperty(value = "通知备注")
    private String notifyMemo;

    @Excel(name = "通知时间" ,dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "通知时间")
    private Date notifyDate;

    @Excel(name = "短信状态")
    @ApiModelProperty(value = "通知结果状态：0.通知失败 1.已取消 2.等待通知 3.已通知")
    private String notifyResult;

    @ApiModelProperty(value = "pt登记时间")
    private Date PtDateregister;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "用户名称")
    private String issueId;


    @Excel(name = "单位名称")
    @ApiModelProperty(value = "单位单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;


    @ApiModelProperty(value = "接受时间")
    private Date joinTime;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "签名图片地址")
    private String signUrl;

}
