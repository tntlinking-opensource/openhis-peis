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
 * JC工程防护种类(ZyFhcsGcClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:55
 */
@Data
@TableName("ZY_FHCS_GC_CLASS")
@ApiModel(value = "ZyFhcsGcClass", description = "JC工程防护种类实体类")
public class ZyFhcsGcClass extends Model<ZyFhcsGcClass> implements Serializable {
    private static final long serialVersionUID = -68355184441443050L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "代码")
    private String defendEngineeringClassCode;

    @ApiModelProperty(value = "名称")
    private String defendEngineeringClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "假删标识")
    private Double isDelete;
}
