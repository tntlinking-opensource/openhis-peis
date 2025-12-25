package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取所有部门 返回数据
 */
@Data
public class AllDepartDataVo implements Serializable {
    private static final long serialVersionUID = 4978591632044488098L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String departName;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

}
