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
 * JC民族(Nation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:08
 */
@Data
@TableName("NATION")
@ApiModel(value = "Nation", description = "JC民族实体类")
public class Nation extends Model<Nation> implements Serializable {
    private static final long serialVersionUID = 546462164504993839L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名族名称")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "罗马代码")
    private String romeCode;

    @ApiModelProperty(value = "字母代码")
    private String wordCode;

    @ApiModelProperty(value = "数字代码")
    private String numberCode;
}
