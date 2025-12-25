package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出Excel数据
 */
@Data
public class RCExportVo implements Serializable {
    private static final long serialVersionUID = -3911196942778654148L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @Excel(name = "体检团体")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "分组名称")
    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "出生日期", dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @Excel(name = "体检类型", readConverterExp = "0=健康体检,1=职业体检,2=综合,3=复查")
    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @Excel(name = "体检套餐")
    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @Excel(name = "是否禁检", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "禁检状态")
    private Integer ispaused;

    @Excel(name = "预约时间", dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "体检者类型")
    private String patientName;

    @Excel(name = "体检者类型")
    @ApiModelProperty(value = "体检者类型")
    private String idPatientclass;

    @ApiModelProperty(value = "确认来检")
    private Integer idPatientclass3;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "体检类型")
    private String idExamsuite;


    @ApiModelProperty(value = "禁检")
    private Integer fPaused;


    @ApiModelProperty(value = "登记员")
    private String userName;


    @ApiModelProperty(value = "组启用")
    private Integer fGroupstarted;

    @ApiModelProperty(value = "组禁用")
    private Integer fGrouppaused;

    @ApiModelProperty(value = "条码打印次数")
    private Integer tmyd;

    @ApiModelProperty(value = "导引单打印次数")
    private Integer guideSignleCount;


    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "体检者来源：关联sys_business_source表source_id")
    private Integer countreportoccupation;

    @ApiModelProperty(value = "第三方平台（平安、康淘等）确认：0.待确认 1.已确认")
    private Integer fOutpatient;

    @ApiModelProperty(value = "电话")
    private String phone;


}
