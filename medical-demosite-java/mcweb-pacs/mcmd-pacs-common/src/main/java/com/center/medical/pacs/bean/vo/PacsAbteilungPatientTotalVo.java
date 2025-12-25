package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取体检者总计
 * @author xhp
 * @since 2023-04-06 15:11
 */
@Data
public class PacsAbteilungPatientTotalVo {

    @ApiModelProperty(value = "合计总人数")
    private int total;

    @ApiModelProperty(value = "已检人数")
    private int examined;

    @ApiModelProperty(value = "未检人数")
    private int unexamined;
}
