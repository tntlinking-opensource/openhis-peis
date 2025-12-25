package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取审核之后的返回数据
 */
@Data
public class NkCheckedDataVo implements Serializable {
    private static final long serialVersionUID = -5217784324855369582L;

    @ApiModelProperty(value = "是否弃检")
    private Integer qij;

    @ApiModelProperty(value = "是否危急值")
    private String wjzjb;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "检查项目名称")
    private String jxcmmc;

    @ApiModelProperty(value = "体征词名称（勾选了就有这个）")
    private String tzcmc;

    @ApiModelProperty(value = "描述")
    private String ms;

    @ApiModelProperty(value = "自由输入结果")
    private String inputResult;

}
