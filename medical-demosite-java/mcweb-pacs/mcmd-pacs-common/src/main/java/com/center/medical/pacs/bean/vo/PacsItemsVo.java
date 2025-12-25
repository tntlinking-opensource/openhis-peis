package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-04-03 9:28
 */
@Data
public class PacsItemsVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "是否启用")
    private String sysmanmark;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "打印排列序号")
    private String xh;

    @ApiModelProperty(value = "性别 0男1女2通用")
    private String xb;

    @ApiModelProperty(value = "收费项目接口代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "部位IDs")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;
}
