package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC重点询问症状表(EmphasisAskSymptom)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_emphasis_ask_symptom")
@ApiModel(value = "EmphasisAskSymptom", description = "JC重点询问症状表实体类")
public class EmphasisAskSymptom extends Model<EmphasisAskSymptom> implements Serializable {
    private static final long serialVersionUID = 728679809626728779L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素种类ID")
    private String danagerId;

    @ApiModelProperty(value = "职业体检类别，详见：ZYTJLB")
    private Integer disiaseType;

    @ApiModelProperty(value = "症状ID")
    private String symptomId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "危害因素名称")
    private String harmname;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "症状代码")
    private String symptomcode;

    @ApiModelProperty(value = "症状名称")
    private String symptom;


    @TableField(exist = false)
    @ApiModelProperty(value = "md_harm表的危害因素名字")
    private String mdHarmName;
}
