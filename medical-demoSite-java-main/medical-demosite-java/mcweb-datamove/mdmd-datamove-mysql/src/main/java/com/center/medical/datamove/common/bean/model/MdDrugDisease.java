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
 * jc禁忌疾病(MdDrugDisease)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Data
@TableName("md_drug_disease")
@ApiModel(value = "MdDrugDisease", description = "jc禁忌疾病实体类")
public class MdDrugDisease extends Model<MdDrugDisease> implements Serializable {
    private static final long serialVersionUID = -87469516130766383L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "禁忌疾病名称")
    private String healthEvaluation;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "禁忌疾病类型id")
    private String typeId;

    @ApiModelProperty(value = "${column.comment}")
    private String isDelete;
}
