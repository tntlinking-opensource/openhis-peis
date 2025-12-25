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
 * 企业经济类型(Qyjjlx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:11
 */
@Data
@TableName("QYJJLX")
@ApiModel(value = "Qyjjlx", description = "企业经济类型实体类")
public class Qyjjlx extends Model<Qyjjlx> implements Serializable {
    private static final long serialVersionUID = -28109057589346567L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "企业经济类型名称")
    private String qyjjlx;
}
