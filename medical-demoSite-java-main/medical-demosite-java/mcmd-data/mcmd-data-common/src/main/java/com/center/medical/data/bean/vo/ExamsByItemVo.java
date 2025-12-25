package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售管理-套餐管理获取右侧表格子表格数据
 */
@Data
public class ExamsByItemVo implements Serializable {
    private static final long serialVersionUID = 7530893675148366699L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;



}
