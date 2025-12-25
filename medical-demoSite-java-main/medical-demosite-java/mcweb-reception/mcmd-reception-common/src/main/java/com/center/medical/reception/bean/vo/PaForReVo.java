package com.center.medical.reception.bean.vo;

import com.center.medical.bean.enums.MedicalType;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 最近体检人员列表返回数据
 */
@Data
public class PaForReVo implements Serializable {
    private static final long serialVersionUID = -2253515374809910642L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "登记日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "任务分组")
    @ApiModelProperty(value = "分组名称")
    private String groupname;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "出生日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "体检者类型")
    private String patientName;

    @Excel(name = "体检者类型")
    @ApiModelProperty(value = "体检者类型")
    private String patientName1;

    @Excel(name = "体检类型", readConverterExp = "0=健康体检,1=职业体检,2=综合,3=复查")
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    /**
     * @see MedicalType
     */
    @Excel(name = "体检类别", readConverterExp = "0=上岗前,1=在岗期间,2=离岗时,3=离岗后,4=应急")
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "套餐名称")
    private String tcmc;

    @Excel(name = "体检日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @Excel(name = "健康报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "健康")
    private String jk;

    @Excel(name = "职业报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "职业")
    private String zy;

    @Excel(name = "是否已结案", readConverterExp = "null=未结案,0=未结案,1=已结案")
    @ApiModelProperty(value = "结案")
    private Integer close;

    @Excel(name = "是否已归档", readConverterExp = "0=未归档,1=已归档")
    @ApiModelProperty(value = "归档，0查询体检者表,1查询体检者历史表")
    private String dbtype;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "体检套餐ID")
    private String idExamsuite;


    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;


    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "分组id")
    private String idgroup;



    @ApiModelProperty(value = "是否交单：0.否 1.是")
    private Integer countreportcompare;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "业务编号")
    private String patientbizno;

    @ApiModelProperty(value = "是否替检：0.否 1.是")
    private Integer countreportxml;

    @ApiModelProperty(value = "替检人")
    private String tjr;

    @ApiModelProperty(value = "pdf连接")
    private String pdfUrl;


    @ApiModelProperty(value = "体检者来源：关联sys_business_source表source_id")
    private Integer countreportoccupation;

    @ApiModelProperty(value = "第三方平台（平安、康淘等）确认：0.待确认 1.已确认")
    private Integer fOutpatient;


    @ApiModelProperty(value = "是否加急：0.否 1.是")
    private Integer isjj;


    @ApiModelProperty(value = "隐私报告是否已生成")
    private String orgDepartsubb;

    @ApiModelProperty(value = "检验报告是否已生成")
    private String jyPdfcreated;

    @ApiModelProperty(value = "临时报告是否已生成")
    private String fPdfcreated;

    @ApiModelProperty(value = "是否包含隐私报告")
    private String containsPrivate;

    @ApiModelProperty(value = "预付方式")
    private String payway;

    @ApiModelProperty(value = "临时报告pdf")
    private String lsPdf;

    @ApiModelProperty(value = "隐私报告pdf")
    private String ysPdf;

    @ApiModelProperty(value = "检验报告pdf")
    private String jyPdf;
}
