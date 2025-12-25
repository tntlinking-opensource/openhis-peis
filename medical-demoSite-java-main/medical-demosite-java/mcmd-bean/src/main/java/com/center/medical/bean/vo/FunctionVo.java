package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-12-08 10:11
 * @description: 业务功能数据
 */
@Data
@ApiModel(value = "业务功能数据", description = "业务功能数据")
public class FunctionVo implements Serializable {
    private static final long serialVersionUID = 5514527168231359945L;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "业务功能名称")
    private String funcName;

    @ApiModelProperty(value = "接口集合")
    private List<InterfaceVo> interfaceVoList;

    @ApiModelProperty(value = "业务功能说明")
    private String remark;

    public FunctionVo(String moduleName, String funcName, List<InterfaceVo> interfaceVoList, String remark) {
        this.moduleName = moduleName;
        this.funcName = funcName;
        this.interfaceVoList = interfaceVoList;
        this.remark = remark;
    }
}
