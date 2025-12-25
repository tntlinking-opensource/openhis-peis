package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量添加用户和分中心配置参数
 */
@Data
public class AddSysUserBranchParam implements Serializable {

    private static final long serialVersionUID = 5277740028845800082L;


    @ApiModelProperty(value = "用户编号")
    private List<String> userNos;

    @ApiModelProperty(value = "分中心")
    private String fzx;
}
