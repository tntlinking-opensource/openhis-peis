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
 * JC职业症状(OccupationSymptom)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:21
 */
@Data
@TableName("OCCUPATION_SYMPTOM")
@ApiModel(value = "OccupationSymptom", description = "JC职业症状实体类")
public class OccupationSymptom extends Model<OccupationSymptom> implements Serializable {
    private static final long serialVersionUID = 509168679296947456L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "症状编码")
    private String symptomCode;

    @ApiModelProperty(value = "症状")
    private String symptom;

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
