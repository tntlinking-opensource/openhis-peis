package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告分享和报告关系表(ReportShareTwo)表实体类
 *
 * @author ay
 * @since 2023-09-19 16:24:24
 */
@Data
@TableName("md_report_share_two")
@ApiModel(value = "ReportShareTwo", description = "报告分享和报告关系表实体类")
public class ReportShareTwo extends Model<ReportShareTwo> implements Serializable {
    private static final long serialVersionUID = -82040763166694915L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "md_report_share_main表的id")
    private String mainId;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "团体ID")
    private String idOrg;


    @ApiModelProperty(value = "团体名称")
    private String orgName;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "性别ID")
    private Integer idSex;


    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "登记时间")
    private Date dateregister;


    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "创建人姓名")
    private String createName;


    @ApiModelProperty(value = "创建人id")
    private String createId;



}
