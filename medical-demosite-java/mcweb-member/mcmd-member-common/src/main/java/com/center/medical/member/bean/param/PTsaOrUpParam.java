package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个检预检回访添加保存参数
 */
@Data
public class PTsaOrUpParam implements Serializable {
    private static final long serialVersionUID = -8628259496245375406L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "单位")
    private String dw;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "费用")
    private String moneyamount;

    @ApiModelProperty(value = "支付方式")
    private String payways;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "上次体检时间")
    private Date dateregister;

    @ApiModelProperty(value = "来检次数")
    private String count;

    @ApiModelProperty(value = "项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "用于存放该检查项目下的所有体证词所拼接的字符串。")
    private String signList;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "是否来检：0是 1.否  2再通知 ")
    private Integer isInspect;

    @ApiModelProperty(value = "来检时间")
    private Date inspectTime;

    @ApiModelProperty(value = "回访备注")
    private String memo;
}
