package com.center.medical.system.bean.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:01
 * @description: 所有科室列表数据
 */
@Data
public class DeptSimpleVo implements Serializable {

    /**
     * 部门编号，对应原体检系统部门id
     */
    @ApiModelProperty(value = "部门编号，对应原体检系统部门id")
    @JsonProperty(value = "id")
    private String deptNo;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @JsonProperty(value = "name")
    private String deptName;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

}
