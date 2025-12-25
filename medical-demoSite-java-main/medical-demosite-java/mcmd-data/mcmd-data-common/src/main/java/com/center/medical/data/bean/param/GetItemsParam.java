package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PACS登记信息查询-登记-右上方收费项目
 */
@Data
public class GetItemsParam implements Serializable {

    private static final long serialVersionUID = -6298318594437897492L;

    @ApiModelProperty(value = "idLabtype")
    private String idLabtype;

    @ApiModelProperty(value = "idDepart")
    private String idDepart;

    @ApiModelProperty(value = "thiredTj")
    private String thiredTj;

    @ApiModelProperty(value = "thiredSex")
    private String thiredSex;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;
}
