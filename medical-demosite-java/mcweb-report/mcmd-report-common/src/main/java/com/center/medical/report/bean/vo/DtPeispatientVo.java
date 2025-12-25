package com.center.medical.report.bean.vo;

import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.Jktjzt;
import com.center.medical.bean.enums.MarriageType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业总检体检者数据
 */
@Data
public class DtPeispatientVo implements Serializable {
    private static final long serialVersionUID = 55971882165284123L;

    @ApiModelProperty(value = "体检者记录ID")
    private String id;

    @ApiModelProperty(value = "重症级别")
    private Integer severedegree;

    @ApiModelProperty(value = "档案号")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "总检锁定")
    private Integer fFinallocked;

    @ApiModelProperty(value = "总检完成")
    private Double fFinalexamed;

    @ApiModelProperty(value = "报告已打印")
    private Integer fReportprinted;

    @ApiModelProperty(value = "报告已取")
    private Integer fReportfetched;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "总检时间")
    private Date datefinalexamed;

    @ApiModelProperty(value = "总检医生")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "体检者类型")
    private String patientclass;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;


    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    /**
     * @see MarriageType
     */
    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;


    @ApiModelProperty(value = "职业体检状态")
    private String zytjzt;

    @ApiModelProperty(value = "职业审核医师")
    private String zyshys;

    @ApiModelProperty(value = "职业总检医生")
    private String patientnameencoded;

    @ApiModelProperty(value = "总检时间")
    private Date dateregisternotime;

    @ApiModelProperty(value = "职业锁定完成")
    private Double idGuidenurse;

}
