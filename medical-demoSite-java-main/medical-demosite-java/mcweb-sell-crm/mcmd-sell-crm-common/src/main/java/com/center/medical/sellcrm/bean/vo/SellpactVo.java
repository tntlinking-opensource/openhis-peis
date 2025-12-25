package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SellpactVo implements Serializable {

    private static final long serialVersionUID = 4827677270429213327L;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @ApiModelProperty(value = "客户电话")
    private String khdh;

    @ApiModelProperty(value = "客户单位注册地址")
    private String khdwzcdz;

    @ApiModelProperty(value = "最早的登记时间")
    private String dateregister;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户状态：0潜在 1正式 2释放")
    private Integer khzt;


}
