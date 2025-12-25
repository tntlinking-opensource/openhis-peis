package com.center.medical.report.bean.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业意见 界面 数据
 */
@Data
public class TreatmentDataVo implements Serializable {

    private static final long serialVersionUID = 8884740033018517631L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "职业处理意见ID（基础数据中的）")
    private String progessionalId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "职业体检类别 0上岗前,1在岗期间,2离岗时,3离岗后,4应急,空未知")
    private String regimentationNote;


    @ApiModelProperty(value = "结论")
    private String occupationSummary;

    @ApiModelProperty(value = "结论")
    private String occupationSummaryId;

    @ApiModelProperty(value = "危害因素")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "疾病")
    private String zyjjzdm;

    @ApiModelProperty(value = "职业病名称")
    private String kyzyb;


}
