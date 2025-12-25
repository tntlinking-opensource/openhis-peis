package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结论词表格数据 返回数据
 */
@Data
public class PacsGetJlcGridVo implements Serializable {
    private static final long serialVersionUID = 1298006894192418773L;


    @ApiModelProperty(value = "pacs体征词id")
    private String tzcid;

    @ApiModelProperty(value = "结论词id")
    private String jlcid;

    @ApiModelProperty(value = "体证词名称")
    private String tzcname;

    @ApiModelProperty(value = "pacs体征词详细描述")
    private String bodyDetail;

    @ApiModelProperty(value = "结论词名称")
    private String jlcName;

    @ApiModelProperty(value = "是否阳性结果：0或null.否 1.是")
    private Integer isPositive;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "1勾选 0不勾选(如果以前保存或审核过这个项目，自动勾选勾选的)")
    private String isSelected;

    @ApiModelProperty(value = "pacs体征词拼音首字母缩写")
    private String tzcCode;

    @ApiModelProperty(value = "结论词拼音首字母缩写")
    private String jlcCode;

    @ApiModelProperty(value = "1默认 0非默认（如果没有保存或审核过这个项目，自动勾选默认的）")
    private Integer isDefault;

    @ApiModelProperty(value = "是否阳性结果")
    private String positive;

}
