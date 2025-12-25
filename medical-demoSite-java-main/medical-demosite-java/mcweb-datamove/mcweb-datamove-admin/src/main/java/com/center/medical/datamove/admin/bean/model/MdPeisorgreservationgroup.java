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
 * 体检者任务分组(MdPeisorgreservationgroup)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:53:28
 */
@Data
@TableName("md_peisorgreservationgroup")
@ApiModel(value = "MdPeisorgreservationgroup", description = "体检者任务分组实体类")
public class MdPeisorgreservationgroup extends Model<MdPeisorgreservationgroup> implements Serializable {
    private static final long serialVersionUID = -53483808458955742L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "ID_ORGRESERVATIONGROUP")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "任务ID")
    private String idOrgreservation;

    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "行序")
    private Integer dispOrder;

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
    private Integer countexaminee;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "折扣率")
    private Double offpercent;

    @ApiModelProperty(value = "统收限额")
    private Double grouppricelimit;

    @ApiModelProperty(value = "组启用")
    private Integer fGroupstarted;

    @ApiModelProperty(value = "组禁用")
    private Integer fGrouppaused;

    @ApiModelProperty(value = "体检者分类ID")
    private String idPatientclass;

    @ApiModelProperty(value = "密级")
    private Integer confidentiallevel;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "ID_EXAMPLACE")
    private Integer idExamplace;

    @ApiModelProperty(value = "GUIDANCENOTEGROUP")
    private String guidancenotegroup;

    @ApiModelProperty(value = "F_USECODEHIDENGROUP")
    private Integer fUsecodehidengroup;

    @ApiModelProperty(value = "ID_PATIENTCLASS2")
    private Integer idPatientclass2;

    @ApiModelProperty(value = "ID_PATIENTCLASS3")
    private Integer idPatientclass3;

    @ApiModelProperty(value = "KEYOCCUDISEASEDUTYSTATUS")
    private String keyoccudiseasedutystatus;

    @ApiModelProperty(value = "HAZARDFACTORSUITE")
    private String hazardfactorsuite;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.正常 1.删除")
    private Integer isDelete;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "微信小程序是否展示金额等内容：1隐藏 其他展示")
    private Integer isShowMoney;
}
