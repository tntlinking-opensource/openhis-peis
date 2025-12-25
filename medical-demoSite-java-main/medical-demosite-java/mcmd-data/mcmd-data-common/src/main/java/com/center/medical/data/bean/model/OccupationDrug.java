package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC职业病/禁忌症(OccupationDrug)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_occupation_drug")
@ApiModel(value = "OccupationDrug", description = "JC职业病/禁忌症实体类")
public class OccupationDrug extends Model<OccupationDrug> implements Serializable {
    private static final long serialVersionUID = 330530250628434939L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素ID")
    private String harmId;

    @ApiModelProperty(value = "在岗阶段")
    private Integer poststage;

    @ApiModelProperty(value = "目标疾病(职业病)")
    private String industrialDisease;

    @ApiModelProperty(value = "目标疾病(禁忌症)")
    private String contraindication;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "诊断依据")
    private String diagnosis;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否是公共的：0或null.否 1.是")
    private Double isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "危害因素名称")
    private String harmName;
}
