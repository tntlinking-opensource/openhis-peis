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
 * 与迟检、阳性、不合格样本回访表一对多关联，(MdVisitMain)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:30
 */
@Data
@TableName("md_visit_main")
@ApiModel(value = "MdVisitMain", description = "与迟检、阳性、不合格样本回访表一对多关联，实体类")
public class MdVisitMain extends Model<MdVisitMain> implements Serializable {
    private static final long serialVersionUID = -70704919125301794L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "是否已来检：1已来检，0未来检")
    private Integer isInspect;

    @ApiModelProperty(value = "来检时间")
    private Date inspectTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否已检：0已检 1未已检")
    private Integer isInspected;

    @ApiModelProperty(value = "回访类型：0：迟检；1：阳性结果；2：不合格样本 //3.补检(护理登记用到 ) 之前写的3，实际上护理登记补检处理成4")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "样本不合格原因ID")
    private String belowquestion;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;
}
