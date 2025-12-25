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
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:52
 */
@Data
@TableName("VISIT_MAIN")
@ApiModel(value = "VisitMain", description = "与迟检、阳性、不合格样本回访表一对多关联，实体类")
public class VisitMain extends Model<VisitMain> implements Serializable {
    private static final long serialVersionUID = 710944141339472930L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "是否来检")
    private Double isInspect;

    @ApiModelProperty(value = "来检时间")
    private Date inspectTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    private Date modifydate;

    @ApiModelProperty(value = "标记在预约时间是否来体检了")
    private Double isInspected;

    @ApiModelProperty(value = "0：迟检；1：阳性结果；2：不合格样本")
    private Double type;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "样本不合格原因ID")
    private String belowquestion;

    @ApiModelProperty(value = "假删")
    private Integer isDelete;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;
}
