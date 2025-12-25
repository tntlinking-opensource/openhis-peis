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
 * JC职业病名称(MdOccupationDiseast)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
@Data
@TableName("md_occupation_diseast")
@ApiModel(value = "MdOccupationDiseast", description = "JC职业病名称实体类")
public class MdOccupationDiseast extends Model<MdOccupationDiseast> implements Serializable {
    private static final long serialVersionUID = -60294645955218386L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "职业病编码")
    private String occupationDiseastCode;

    @ApiModelProperty(value = "职业病名称")
    private String occupationDiseast;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "职业病分类ID")
    private String occupationDiseastClass;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
