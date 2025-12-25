package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存表格数据
 */
@Data
public class GriddataDto implements Serializable {
    private static final long serialVersionUID = 7660838751183007376L;


    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;
}
