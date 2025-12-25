package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结论词表格数据 参数
 */
@Data
public class PacsGetJlcGridParam implements Serializable {
    private static final long serialVersionUID = -6618565075663158332L;


    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
