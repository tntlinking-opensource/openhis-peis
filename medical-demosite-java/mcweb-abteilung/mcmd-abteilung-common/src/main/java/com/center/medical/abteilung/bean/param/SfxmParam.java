package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室加项左侧数据，创建套餐获取基础数据收费项目参数
 */
@Data
public class SfxmParam implements Serializable {
    private static final long serialVersionUID = -7441213527564553063L;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "适用性别")
    private String syxb;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "分中心id")
    private List<String> fzxid;

    @ApiModelProperty(value = "科室加项不显示价格为0的，填1")
    private String addItem;

    @ApiModelProperty(value = "体检类型数值")
    private String tjValue;

    @ApiModelProperty(value = "适用性别数值--用于调取不同类别的收费项目")
    private String syxbValue;


}
