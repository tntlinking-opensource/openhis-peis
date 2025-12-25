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
 * 职业危害因素分类(MdZyHarmClass)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
@Data
@TableName("md_zy_harm_class")
@ApiModel(value = "MdZyHarmClass", description = "职业危害因素分类实体类")
public class MdZyHarmClass extends Model<MdZyHarmClass> implements Serializable {
    private static final long serialVersionUID = -65483865708138693L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "危害因_id号")
    private String harId;

    @ApiModelProperty(value = "危害种类代码")
    private String harmClassCode;

    @ApiModelProperty(value = "危害因素种类")
    private String harmClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否删除 0：不删除，1：删除")
    private Integer isDelete;
}
