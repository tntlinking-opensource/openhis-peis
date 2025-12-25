package com.center.medical.bean.model;

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
 * 体检用户账号(PatientUsers)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_patient_users")
@ApiModel(value = "PatientUsers", description = "体检用户账号实体类")
public class PatientUsers extends Model<PatientUsers> implements Serializable {
    private static final long serialVersionUID = -37796271312422385L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "档案ID")
    private String archiveId;

    @ApiModelProperty(value = "激活状态")
    private String status;
}
