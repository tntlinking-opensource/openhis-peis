package com.center.medical.datamove.oracle.bean.model;


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
 * (Yblx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
@Data
@TableName("YBLX")
@ApiModel(value = "Yblx", description = "$tableInfo.comment实体类")
public class Yblx extends Model<Yblx> implements Serializable {
    private static final long serialVersionUID = 982215558382256290L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "样本名称")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "0未删除1删除")
    private Integer isDelete;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "${column.comment}")
    private String xh;
}
