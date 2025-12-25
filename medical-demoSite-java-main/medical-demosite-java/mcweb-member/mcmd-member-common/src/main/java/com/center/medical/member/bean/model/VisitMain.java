package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_visit_main")
@ApiModel(value = "VisitMain", description = "与迟检、阳性、不合格样本回访表一对多关联，实体类")
public class VisitMain extends Model<VisitMain> implements Serializable {
    private static final long serialVersionUID = 306101005847631724L;

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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否已处理,1是 null否")
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

    @TableField(exist = false)
    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;
}
