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
 * JC职业病种分类(OccupationDiseastClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:18
 */
@Data
@TableName("OCCUPATION_DISEAST_CLASS")
@ApiModel(value = "OccupationDiseastClass", description = "JC职业病种分类实体类")
public class OccupationDiseastClass extends Model<OccupationDiseastClass> implements Serializable {
    private static final long serialVersionUID = -61236399207360496L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分类码")
    private String occupationDiseastClassCode;

    @ApiModelProperty(value = "职业病分类名称")
    private String occupationDiseastClassName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "假删")
    private Double isDelete;
}
