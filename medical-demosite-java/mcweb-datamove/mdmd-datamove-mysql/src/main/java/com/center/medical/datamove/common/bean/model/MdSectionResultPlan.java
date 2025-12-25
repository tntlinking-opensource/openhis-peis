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
 * 科室批量录入结果(MdSectionResultPlan)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:34
 */
@Data
@TableName("md_section_result_plan")
@ApiModel(value = "MdSectionResultPlan", description = "科室批量录入结果实体类")
public class MdSectionResultPlan extends Model<MdSectionResultPlan> implements Serializable {
    private static final long serialVersionUID = 450601339632687765L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
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
}
