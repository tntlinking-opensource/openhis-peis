package com.center.medical.reception.bean.vo;

import com.center.medical.bean.enums.ExamType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-01 16:41
 * @description: 前台-登记管理体检者信息
 */
@Data
public class RegisterVo implements Serializable {

    private static final long serialVersionUID = 3528985581811262545L;

    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "体检者ID")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "体检团体")
    private String orgName;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "分组名称")
    private String groupname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "是否禁检：0.否 1.是")
    private Integer ispaused;

    @ApiModelProperty(value = "登记人")
    private String registerR;

    @ApiModelProperty(value = "条码打印次数")
    private Integer tmyd;

    @ApiModelProperty(value = "导引单打印次数")
    private Integer guideSignleCount;

    @ApiModelProperty(value = "预约时间")
    private Date medicaldate;

    @ApiModelProperty(value = "体检者类型（会员等级）")
    private String idPatientClass;

    @ApiModelProperty(value = "体检者来源：关联sys_business_source表source_id")
    private Integer countreportoccupation;

    @ApiModelProperty(value = "第三方平台（平安、康淘等）确认：0.待确认 1.已确认")
    private Integer fOutpatient;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;


    @ApiModelProperty(value = "组启用")
    private Integer fGroupstarted;

    @ApiModelProperty(value = "组禁用")
    private Integer fGrouppaused;

    @ApiModelProperty(value = "禁检")
    private Integer fPaused;

    @ApiModelProperty(value = "是否预收")
    private String sfys;

    @ApiModelProperty(value = "预收金额")
    private String ysje;

    @ApiModelProperty(value = "预检短信 0未发送1已发送")
    private String yjdx;

}
