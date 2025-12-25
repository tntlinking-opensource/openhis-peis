package com.center.medical.bean.vo;

import com.center.medical.bean.enums.ExamStatus;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.Jktjzt;
import com.center.medical.bean.enums.MedicalType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户订单下的体检者名单信息
 *
 * @author 路飞船长
 * @since 2024-11-06 15:14:06
 */
@Data
public class OrderUserVo implements Serializable {

    private static final long serialVersionUID = -5620924216593279498L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "订单号 numorgresv")
    private String orderNum;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别：0.男 1.女 idSex")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "是否预约：0.未预约 1.已预约 fIsforreserve")
    private Integer hadReservation;

    @ApiModelProperty(value = "是否登记：0.未登记 1.已登记 fRegistered")
    private Integer registered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查 idExamtype")
    private Integer examtype;

    @ApiModelProperty(value = "家人手机号")
    private String fmPhone;

    @ApiModelProperty(value = "分检完成：1已完成 fReadytofinal")
    private Integer checked;

    @ApiModelProperty(value = "医院代码")
    private String hospitalcode;

    @ApiModelProperty(value = "是否替检：0.否 1.是 countreportxml")
    private Integer replaced;

    @ApiModelProperty(value = "领取方式")
    private String takeType;

    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "健康报告：0.等待生成报告、1.报告已生成等待领取、11.报告已领取")
    private Integer healthReport;

    /**
     * @see ExamStatus
     */
    @ApiModelProperty(value = "职业报告：0.等待生成报告、1.报告已生成等待领取、11.报告已领取")
    private Integer workReport;

    @ApiModelProperty(value = "工种")
    private String trades;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer medicaltype;

    @ApiModelProperty(value = "原始工种")
    private String originalTrade;

    @ApiModelProperty(value = "销售经理")
    private String sale;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

}
