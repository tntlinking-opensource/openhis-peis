package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2022-12-08 10:11
 * @description: 功能接口数据
 */
@Data
@ApiModel(value = "功能接口数据", description = "功能接口数据")
public class InterfaceVo implements Serializable {
    private static final long serialVersionUID = 5514527168231359945L;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "请求方法：GET、POST、PUT、DELETE")
    private String method;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "Swagger接口位置")
    private String remark;

    @ApiModelProperty(value = "请求参数")
    private Map<String, Object> params;

    public InterfaceVo(String name, String method, String url, String remark, Map<String, Object> params) {
        this.name = name;
        this.method = method;
        this.url = url;
        this.remark = remark;
        this.params = params;
    }
}
