package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取类型返回数据
 */
@Data
public class GetTypeListVo implements Serializable {
    private static final long serialVersionUID = 6826449207705202814L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;
}
