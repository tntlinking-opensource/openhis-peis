package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/12/10 06:09
 * @description: 体检者体检数据
 */
@Data
public class ExamItemDataDto implements Serializable {

    private static final long serialVersionUID = -8223433032707927805L;

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private String id;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String itemName;

    /**
     * 所属科室ID
     */
    @ApiModelProperty(value = "所属科室Id")
    private String departmentId;

    /**
     * 所属科室
     */
    @ApiModelProperty(value = "所属科室")
    private String department;

    /**
     * 检查结果
     */
    @ApiModelProperty(value = "检查结果")
    private String result;

    /**
     * 检查结论
     */
    @ApiModelProperty(value = "检查结论")
    private String conclusion;

    /**
     * 是否阳性：0否 1是
     */
    @ApiModelProperty(value = "是否阳性：0否 1是")
    private Integer positive;

    /**
     * 检查时间
     */
    @ApiModelProperty(value = "检查时间")
    private String examDate;
}
