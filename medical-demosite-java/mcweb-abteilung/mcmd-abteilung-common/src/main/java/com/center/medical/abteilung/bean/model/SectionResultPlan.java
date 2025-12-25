package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室批量录入结果(SectionResultPlan)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:19
 */
@Data
@TableName("md_section_result_plan")
@ApiModel(value = "SectionResultPlan", description = "科室批量录入结果实体类")
public class SectionResultPlan extends Model<SectionResultPlan> implements Serializable {
    private static final long serialVersionUID = 515581134846024277L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "创建人username")
    private String creater;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "状态：0未审核  1已审核(只用于记录是否已被线程审核，实际是否审核判断sectionResultMain)")
    private Integer status;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;


    public SectionResultPlan(String depId, String creater, String patientcode, Integer status, String errorMsg) {
        this.depId = depId;
        this.creater = creater;
        this.patientcode = patientcode;
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public SectionResultPlan() {
    }

}
