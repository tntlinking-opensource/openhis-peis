package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ZJ总检主表(SectionTotal)表实体类
 *
 * @author ay
 * @since 2024-06-05 14:39:41
 */
@Data
@TableName("SECTION_TOTAL")
@ApiModel(value = "SectionTotal", description = "ZJ总检主表实体类")
public class OrSectionTotal extends Model<OrSectionTotal> implements Serializable {
    private static final long serialVersionUID = 738441352966940663L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "总检医生ID")
    private String doctorId;


    @ApiModelProperty(value = "总检时间")
    private Date totalTime;


    @ApiModelProperty(value = "录入人ID")
    private String writeId;


    @ApiModelProperty(value = "录入时间")
    private Date writeTime;


    @ApiModelProperty(value = "综述")
    private Object summarize;


    @ApiModelProperty(value = "职业/健康")
    private Integer diseaseHealth;


    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;


    @ApiModelProperty(value = "${column.comment}")
    private Object offer;


    @ApiModelProperty(value = "${column.comment}")
    private Object verdict;


    @ApiModelProperty(value = "${column.comment}")
    private Object posistive;


    @ApiModelProperty(value = "${column.comment}")
    private Object jkoffer;


    @ApiModelProperty(value = "${column.comment}")
    private Integer scbs;


    @ApiModelProperty(value = "${column.comment}")
    private String reportConclusions;


    @ApiModelProperty(value = "${column.comment}")
    private String summaryId;

}
