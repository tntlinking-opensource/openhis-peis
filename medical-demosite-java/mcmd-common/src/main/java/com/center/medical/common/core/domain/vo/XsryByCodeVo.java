package com.center.medical.common.core.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询销售人员通过输入码
 */
@Data
public class XsryByCodeVo implements Serializable {
    private static final long serialVersionUID = 2392867282883176720L;


    @ApiModelProperty(value = "用户no")
    private String userNo;

    @ApiModelProperty(value = "用户名称")
    private String userName;
}
