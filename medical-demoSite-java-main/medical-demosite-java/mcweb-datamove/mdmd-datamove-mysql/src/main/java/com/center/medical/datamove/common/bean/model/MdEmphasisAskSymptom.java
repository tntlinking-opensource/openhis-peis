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
 * JC重点询问症状表(MdEmphasisAskSymptom)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
@Data
@TableName("md_emphasis_ask_symptom")
@ApiModel(value = "MdEmphasisAskSymptom", description = "JC重点询问症状表实体类")
public class MdEmphasisAskSymptom extends Model<MdEmphasisAskSymptom> implements Serializable {
    private static final long serialVersionUID = -37660095431698037L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素种类ID")
    private String danagerId;

    @ApiModelProperty(value = "职业体检类别，详见：com.world.center.bean.enums.MedicalType")
    private String disiaseType;

    @ApiModelProperty(value = "症状ID")
    private String symptomId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素名称")
    private String harmname;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "症状代码")
    private String symptomcode;

    @ApiModelProperty(value = "症状名称")
    private String symptom;
}
