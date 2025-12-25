package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目表格数据 返回数据
 */
@Data
public class PacsGetItemsDataVo implements Serializable {
    private static final long serialVersionUID = -9098759931555876594L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID（暂未使用）")
    private String itemId;

    @ApiModelProperty(value = "partName")
    private String partName;

    @ApiModelProperty(value = "检查项目打印名称")
    private String itemName;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer FExaminated;

    @ApiModelProperty(value = "操作时间")
    private String chargeTime;

    @ApiModelProperty(value = "已检未检")
    private String examed;
}
