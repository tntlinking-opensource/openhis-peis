package com.center.medical.reception.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS外送登记结果主表(OutsideMain)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_outside_main")
@ApiModel(value = "OutsideMain", description = "KS外送登记结果主表实体类")
public class OutsideMain extends Model<OutsideMain> implements Serializable {
    private static final long serialVersionUID = -91275131574155345L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "外送人")
    private String sendPeople;

    @ApiModelProperty(value = "外送时间")
    private Date sendDate;

    @ApiModelProperty(value = "返回人")
    private String backPeople;

    @ApiModelProperty(value = "返回时间")
    private Date backDate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "处理状态")
    private Integer status;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @TableField(exist = false)
    @ApiModelProperty(value = "性别")
    private Integer xb;


    @TableField(exist = false)
    @ApiModelProperty(value = "外送人姓名")
    private String sendPeopleName;

    @TableField(exist = false)
    @ApiModelProperty(value = "格式化成yyyy-MM-dd的外送时间")
    private String sendDateString;
}
