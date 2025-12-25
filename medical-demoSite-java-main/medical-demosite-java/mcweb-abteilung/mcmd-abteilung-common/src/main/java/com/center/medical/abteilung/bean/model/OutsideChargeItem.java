package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS外送项目表(OutsideChargeItem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_outside_charge_item")
@ApiModel(value = "OutsideChargeItem", description = "KS外送项目表实体类")
public class OutsideChargeItem extends Model<OutsideChargeItem> implements Serializable {
    private static final long serialVersionUID = -39939246392274818L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目id")
    private String idCharge;

    @ApiModelProperty(value = "部门id")
    private String idDepart;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "外送机构id")
    private String wsjgId;
}
