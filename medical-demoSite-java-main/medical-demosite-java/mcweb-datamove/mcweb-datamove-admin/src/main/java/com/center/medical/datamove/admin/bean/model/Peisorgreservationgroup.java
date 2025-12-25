package com.center.medical.datamove.admin.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * QT体检团体任务分组表(Peisorgreservationgroup)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:54:58
 */
@Data
@TableName("PEISORGRESERVATIONGROUP")
@ApiModel(value = "Peisorgreservationgroup", description = "QT体检团体任务分组表实体类")
public class Peisorgreservationgroup extends Model<Peisorgreservationgroup> implements Serializable {
    private static final long serialVersionUID = -13823904751452676L;

    @ApiModelProperty(value = "ID_ORGRESERVATIONGROUP")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "任务ID")
    private String idOrgreservation;

    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "行序")
    private Double dispOrder;

    @ApiModelProperty(value = "单位名称")
    private String orgName;

    @ApiModelProperty(value = "计划体检日期")
    private Date dateexamplanned;

    @ApiModelProperty(value = "指示")
    private String instruction;

    @ApiModelProperty(value = "男")
    private Integer fMale;

    @ApiModelProperty(value = "女")
    private Integer fFemale;

    @ApiModelProperty(value = "已婚")
    private Integer fHasmarried;

    @ApiModelProperty(value = "未婚")
    private Integer fNevermarried;

    @ApiModelProperty(value = "年龄下限")
    private Integer agemin;

    @ApiModelProperty(value = "年龄上限")
    private Integer agemax;

    @ApiModelProperty(value = "组类")
    private String grouptype;

    @ApiModelProperty(value = "体检类型ID")
    private String idExamtype;

    @ApiModelProperty(value = "体检套餐ID")
    private String idExamsuite;

    @ApiModelProperty(value = "EXAMSUITE_ALIAS")
    private String examsuiteAlias;

    @ApiModelProperty(value = "体检者数量")
    private Double countexaminee;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "折扣率")
    private Double offpercent;

    @ApiModelProperty(value = "统收限额")
    private Double grouppricelimit;

    @ApiModelProperty(value = "组启用")
    private Double fGroupstarted;

    @ApiModelProperty(value = "组禁用")
    private Double fGrouppaused;

    @ApiModelProperty(value = "体检者分类ID")
    private String idPatientclass;

    @ApiModelProperty(value = "密级")
    private Double confidentiallevel;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "ID_EXAMPLACE")
    private Double idExamplace;

    @ApiModelProperty(value = "GUIDANCENOTEGROUP")
    private String guidancenotegroup;

    @ApiModelProperty(value = "F_USECODEHIDENGROUP")
    private Integer fUsecodehidengroup;

    @ApiModelProperty(value = "ID_PATIENTCLASS2")
    private Double idPatientclass2;

    @ApiModelProperty(value = "ID_PATIENTCLASS3")
    private Double idPatientclass3;

    @ApiModelProperty(value = "KEYOCCUDISEASEDUTYSTATUS")
    private String keyoccudiseasedutystatus;

    @ApiModelProperty(value = "HAZARDFACTORSUITE")
    private String hazardfactorsuite;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "0:不删除 1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isShowMoney;
}
