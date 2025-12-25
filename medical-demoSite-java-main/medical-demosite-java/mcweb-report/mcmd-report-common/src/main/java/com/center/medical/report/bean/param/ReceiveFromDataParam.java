package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ReceiveFromDataParam implements Serializable {

    private static final long serialVersionUID = -2258134615497005204L;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "领取人")
    private String getterName;

    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @ApiModelProperty(value = "领取人电话")
    private String getterPhone;

    @ApiModelProperty(value = "发放方式")
    private String grantId;

    @ApiModelProperty(value = "快递号")
    private String expressNum;

    @ApiModelProperty(value = "快递公司")
    private String expressId;

    @ApiModelProperty(value = "签名图片路径")
    private String signUrl;


}
