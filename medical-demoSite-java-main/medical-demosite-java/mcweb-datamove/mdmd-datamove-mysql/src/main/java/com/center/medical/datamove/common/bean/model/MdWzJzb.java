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
 * KS问诊——家族病(MdWzJzb)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:23
 */
@Data
@TableName("md_wz_jzb")
@ApiModel(value = "MdWzJzb", description = "KS问诊——家族病实体类")
public class MdWzJzb extends Model<MdWzJzb> implements Serializable {
    private static final long serialVersionUID = -62049420496545404L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "病名")
    private String familyOfDisease;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}
