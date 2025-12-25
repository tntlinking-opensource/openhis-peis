package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItemsDetailDto implements Serializable {


    @ApiModelProperty(value = "iid")
    private String iid;

    @ApiModelProperty(value = "eid")
    private String eid;

    @ApiModelProperty(value = "sid")
    private String sid;

    @ApiModelProperty(value = "cid")
    private String cid;

    @ApiModelProperty(value = "体证词名称")
    private String name;

    @ApiModelProperty(value = "体证词详细描述")
    private String bodyDetail;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;
}
