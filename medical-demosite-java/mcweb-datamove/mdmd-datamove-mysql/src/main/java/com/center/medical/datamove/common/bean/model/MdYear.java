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
 * 年份表(MdYear)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
@Data
@TableName("md_year")
@ApiModel(value = "MdYear", description = "年份表实体类")
public class MdYear extends Model<MdYear> implements Serializable {
    private static final long serialVersionUID = 618814576696621081L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "父节点")
    private String parentname;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}
