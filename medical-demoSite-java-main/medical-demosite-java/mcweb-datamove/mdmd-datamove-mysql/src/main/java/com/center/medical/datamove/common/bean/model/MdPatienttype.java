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
 * 体检者类型(MdPatienttype)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Data
@TableName("md_patienttype")
@ApiModel(value = "MdPatienttype", description = "体检者类型实体类")
public class MdPatienttype extends Model<MdPatienttype> implements Serializable {
    private static final long serialVersionUID = 524276531777252040L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
