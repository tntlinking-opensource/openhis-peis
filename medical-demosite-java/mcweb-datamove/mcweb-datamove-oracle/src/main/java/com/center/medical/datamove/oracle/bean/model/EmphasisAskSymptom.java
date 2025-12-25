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
 * JC重点询问症状表(EmphasisAskSymptom)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:36
 */
@Data
@TableName("EMPHASIS_ASK_SYMPTOM")
@ApiModel(value = "EmphasisAskSymptom", description = "JC重点询问症状表实体类")
public class EmphasisAskSymptom extends Model<EmphasisAskSymptom> implements Serializable {
    private static final long serialVersionUID = 706959335508341997L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素种类ID")
    private String danagerId;

    @ApiModelProperty(value = "0：上岗前；1：在岗期间；2：离职时；3：离岗后；4：应急")
    private Integer disiaseType;

    @ApiModelProperty(value = "症状ID")
    private String symptomId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素名称")
    private String harmname;

    @ApiModelProperty(value = "假删")
    private Integer isDelete;

    @ApiModelProperty(value = "症状代码")
    private String symptomcode;

    @ApiModelProperty(value = "症状名称")
    private String symptom;
}
