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
 * JC工程防护(MdZyFhcsGc)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
@Data
@TableName("md_zy_fhcs_gc")
@ApiModel(value = "MdZyFhcsGc", description = "JC工程防护实体类")
public class MdZyFhcsGc extends Model<MdZyFhcsGc> implements Serializable {
    private static final long serialVersionUID = 179664931472008271L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "代码")
    private String defendEngieeringCode;

    @ApiModelProperty(value = "名称")
    private String defendEngineering;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "工程防护种类代码")
    private String defendEngineeringClass;

    @ApiModelProperty(value = "工程防护种类ID")
    private String gcId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
