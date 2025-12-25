package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取所有的客户数据
 */
@Data
public class AllOrgVo implements Serializable {
    private static final long serialVersionUID = 7419284292144679351L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;


}
