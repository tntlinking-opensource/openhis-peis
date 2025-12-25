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
 * JC检查项目类型表(MdBasexamltemtype)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
@Data
@TableName("md_basexamltemtype")
@ApiModel(value = "MdBasexamltemtype", description = "JC检查项目类型表实体类")
public class MdBasexamltemtype extends Model<MdBasexamltemtype> implements Serializable {
    private static final long serialVersionUID = 919282518414788493L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "EXAMITEMTYPEKEY")
    private String examitemtypekey;

    @ApiModelProperty(value = "检查项目类型名称")
    private String examitemtypeName;

    @ApiModelProperty(value = "检查项目类型英文名称")
    private String examitemtypeNameeng;

    @ApiModelProperty(value = "检查项目类型代码")
    private String examitemtypeCode;

    @ApiModelProperty(value = "导出代码")
    private String examitemtypecodex;

    @ApiModelProperty(value = "部门ID")
    private String idDepartment;

    @ApiModelProperty(value = "部门名称(R)")
    private String departR;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
