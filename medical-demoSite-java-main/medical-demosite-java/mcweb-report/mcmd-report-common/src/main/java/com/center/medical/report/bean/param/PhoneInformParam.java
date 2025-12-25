package com.center.medical.report.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业报告领取通知分页参数
 */
@Data
public class PhoneInformParam  implements Serializable {

    private static final long serialVersionUID = 746454460522868097L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名（模糊查询只有后面有百分号，前面没有）")
    private String name;

    @ApiModelProperty(value = "体检单位")
    private String idOrg;

    @ApiModelProperty(value = "通知结果")
    private String notificationResult;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始交接时间")
    private Date startRevTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束交接时间")
    private Date endRevTime;

    @ApiModelProperty(value = "通知状态")
    private String noticeStatus;

    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @ApiModelProperty(value = "起年龄")
    private String startAge;

    @ApiModelProperty(value = "止年龄")
    private String endAge;

    @ApiModelProperty(value = "检查结论")
    private String occupationSummary;

    @ApiModelProperty(value = "排序 revTime 或 dateregister")
    private String sort;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取开始时间(报告领取使用)")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取结束时间(报告领取使用)")
    private Date endTime;

    @ApiModelProperty(value = "体检者电话(报告领取使用)")
    private String phone;

    @ApiModelProperty(value = "领取状态0未领取1已领取(报告领取使用)")
    private String receiveStatus;

    @ApiModelProperty(value = "领取人(报告领取使用)")
    private String getterName;

    @ApiModelProperty(value = "报告发放人ID(报告领取使用)")
    private String issueId;

    @ApiModelProperty(value = "部门(报告领取使用)")
    private String orgDepart;

    @ApiModelProperty(value = "发送方式ID(报告领取使用)")
    private String grantId;

    @ApiModelProperty(value = "订单号(报告领取使用)")
    private String numorgresv;

    @ApiModelProperty(value = "通知类型， 详见数据表：md_short_message_type")
    private Integer notifyType;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

}
