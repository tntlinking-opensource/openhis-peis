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
 * 体检车与外出体检车上的人员关联表(Carmanagefr)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_carmanagefr")
@ApiModel(value = "Carmanagefr", description = "体检车与外出体检车上的人员关联表实体类")
public class Carmanagefr extends Model<Carmanagefr> implements Serializable {
    private static final long serialVersionUID = -50353798228355809L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "外出体检人员")
    private String idUser;

    @ApiModelProperty(value = "外出体检车上的人员关联ID")
    private String idCarmanage;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
