package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-03-22 10:23
 */
@Data
public class PacsAbteilungItemSearchHistoryVo {
    @ApiModelProperty(value = "最近历史描述")
    private String description;
    @ApiModelProperty(value = "最近历史小结")
    private String conclusions;
}
