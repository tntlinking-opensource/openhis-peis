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
 * ZJ总检主表(MdSectionTotal)表实体类
 *
 * @author ay
 * @since 2023-11-10 14:27:20
 */
@Data
@TableName("md_section_total")
@ApiModel(value = "MdSectionTotal", description = "ZJ总检主表实体类")
public class MdSectionTotal extends Model<MdSectionTotal> implements Serializable {
    private static final long serialVersionUID = -10026005216611474L;

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
    private String summarize;


    @ApiModelProperty(value = "职业/健康")
    private Integer diseaseHealth;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "健康总检：健康建议   职业总检：职业结论及建议 OFFER")
    private String offer;


    @ApiModelProperty(value = "结论")
    private String verdict;


    @ApiModelProperty(value = "阳性结果")
    private String posistive;


    @ApiModelProperty(value = "职业总检：健康建议")
    private String jkoffer;


    @ApiModelProperty(value = "上传标志  0未上传1已上传 无null值")
    private Integer scbs;


    @ApiModelProperty(value = "职业报告结论词")
    private String reportConclusions;


    @ApiModelProperty(value = "zySummary（职业总检）")
    private String summaryId;

}
