package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取当前登录用户分中心下正式客户管理信息
 */
@Data
public class SCListVo implements Serializable {
    private static final long serialVersionUID = -4436843239953792004L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;
}
