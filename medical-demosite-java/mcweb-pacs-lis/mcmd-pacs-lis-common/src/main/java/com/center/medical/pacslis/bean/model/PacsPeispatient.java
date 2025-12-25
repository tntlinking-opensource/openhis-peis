package com.center.medical.pacslis.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-体检者表(PacsPeispatient)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_peispatient")
@ApiModel(value = "PacsPeispatient", description = "PACS-体检者表实体类")
public class PacsPeispatient extends Model<PacsPeispatient> implements Serializable {
    private static final long serialVersionUID = -15980551867373392L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "XID")
    private String id;

    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "预约")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "体检类型ID")
    private String idExamtype;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "pacs登记页面已登记")
    private Integer idExamplace;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "客户证件类型，详见：com.world.center.bean.enums.CusCardType")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "黑名单备注")
    private String isHmdb;

    @ApiModelProperty(value = "黑名单")
    private Integer isHmd;

    @ApiModelProperty(value = "健康体检状态，详见：com.world.center.bean.enums.jktjzt")
    private Integer jktjzt;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @TableField(exist = false)
    @ApiModelProperty(value = "上传标示")
    private Integer scbs;

    @TableField(exist = false)
    @ApiModelProperty(value = "分拣完成时间")
    private Date readytofinalDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心id（医院代码）")
    private String hospitalcode;

    @TableField(exist = false)
    @ApiModelProperty(value = "禁检")
    private Integer fPaused;

    @TableField(exist = false)
    @ApiModelProperty(value = "性别")
    private String sex;

    @TableField(exist = false)
    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @TableField(exist = false)
    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @TableField(exist = false)
    @ApiModelProperty(value = "接害时间")
    private Date harmDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @TableField(exist = false)
    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @TableField(exist = false)
    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;
}
