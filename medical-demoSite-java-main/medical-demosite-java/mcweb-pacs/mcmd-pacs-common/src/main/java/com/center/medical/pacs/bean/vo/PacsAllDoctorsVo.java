package com.center.medical.pacs.bean.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 监察人、审核人下拉数据
 */
@Data
public class PacsAllDoctorsVo implements Serializable {
    private static final long serialVersionUID = -1621872354515715280L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

}
