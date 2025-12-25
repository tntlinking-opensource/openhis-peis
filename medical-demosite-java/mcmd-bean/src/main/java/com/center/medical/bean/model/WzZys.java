package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS问诊——职业史(WzZys)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_wz_zys")
@ApiModel(value = "WzZys", description = "KS问诊——职业史实体类")
public class WzZys extends Model<WzZys> implements Serializable {
    private static final long serialVersionUID = -94244679404894002L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "档案号")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "开始年月")
    private Date startDate;

    @ApiModelProperty(value = "截止年月")
    private Date stopDate;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "部门")
    private String branch;

    @ApiModelProperty(value = "工种")
    private String typeOfWork;

    @ApiModelProperty(value = "有无有害因素")
    private String occupationHarm;

    @ApiModelProperty(value = "有无检测")
    private String localeTestAmount;

    @ApiModelProperty(value = "DEFEND_MANAGE")
    private String defendManage;

    @ApiModelProperty(value = "有无告知")
    private String occupationBargain;

    @ApiModelProperty(value = "有无防护,0无，1有")
    private String occupationDefend;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "治疗经过")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;


    @ApiModelProperty(value = "工作单位ID")
    private String deptId;

    @TableField(exist = false)
    @ApiModelProperty(value = "危害因素（拼接）")
    private String harmName;


    public WzZys(String patientarchiveno, Date stopDate, String dept, String deptId, String branch, String typeOfWork, String occupationHarm, String createId, String djls) {
        super();
        this.idPatientarchive = patientarchiveno;
        this.stopDate = stopDate;
        this.dept = dept;
        this.deptId = deptId;
        this.branch = branch;
        this.typeOfWork = typeOfWork;
        this.occupationHarm = occupationHarm;
        this.createId = createId;
        this.djls = djls;
    }


    public WzZys() {

    }
}
