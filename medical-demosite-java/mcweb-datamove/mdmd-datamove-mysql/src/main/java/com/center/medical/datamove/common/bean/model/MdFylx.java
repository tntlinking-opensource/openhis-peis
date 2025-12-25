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
 * JC费用类型(MdFylx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Data
@TableName("md_fylx")
@ApiModel(value = "MdFylx", description = "JC费用类型实体类")
public class MdFylx extends Model<MdFylx> implements Serializable {
    private static final long serialVersionUID = -94124135955578170L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "开始时间")
    private Date createdate;

    @ApiModelProperty(value = "结束时间")
    private Date modifydate;
}
