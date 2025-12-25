package com.center.medical.pacs.bean.param;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-03-31 14:20
 */
@Data
public class PacsItemsListParam implements Serializable {
    private static final long serialVersionUID = 8835596490901468585L;
    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "性别 0男1女2通用")
    private String xb;

    @ApiModelProperty(value = "所属科室编号")
    private String idDepart;

    @ApiModelProperty(value = "收费项目接口代码")
    private String examfeeitemCode;

    public String getSfxmsrm() {
        return StrUtil.isEmpty(sfxmsrm) ? null : sfxmsrm.toUpperCase();
    }
}
