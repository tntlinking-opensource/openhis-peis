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
 * JC禁忌疾病(DrugDisease)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:25
 */
@Data
@TableName("DRUG_DISEASE")
@ApiModel(value = "DrugDisease", description = "JC禁忌疾病实体类")
public class DrugDisease extends Model<DrugDisease> implements Serializable {
    private static final long serialVersionUID = -43609801745986864L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "禁忌疾病类型ID")
    private String typeId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;
}
