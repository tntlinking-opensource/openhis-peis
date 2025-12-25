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
 * JC费用类型(Fylx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:02
 */
@Data
@TableName("FYLX")
@ApiModel(value = "Fylx", description = "JC费用类型实体类")
public class Fylx extends Model<Fylx> implements Serializable {
    private static final long serialVersionUID = 511880705456663521L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "0未删除1已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "开始时间")
    private Date createdate;

    @ApiModelProperty(value = "结束时间")
    private Date modifydate;
}
