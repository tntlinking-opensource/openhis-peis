package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 体检车与外出体检车上的人员关联表(MdCarmanagefr)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
@Data
@TableName("md_carmanagefr")
@ApiModel(value = "MdCarmanagefr", description = "体检车与外出体检车上的人员关联表实体类")
public class MdCarmanagefr extends Model<MdCarmanagefr> implements Serializable {
    private static final long serialVersionUID = 109288748607358371L;

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
