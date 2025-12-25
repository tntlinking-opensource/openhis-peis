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
 * 体检者类型(Patienttype)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:55
 */
@Data
@TableName("PATIENTTYPE")
@ApiModel(value = "Patienttype", description = "体检者类型实体类")
public class Patienttype extends Model<Patienttype> implements Serializable {
    private static final long serialVersionUID = -18401967394087916L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String patientName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "如果为1在科室显示红色名称")
    private Integer flag;
}
