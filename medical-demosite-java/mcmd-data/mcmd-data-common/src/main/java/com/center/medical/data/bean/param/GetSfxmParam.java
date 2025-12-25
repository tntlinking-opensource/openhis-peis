package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建套餐-获取基础数据的收费项目参数
 */
@Data
public class GetSfxmParam implements Serializable {
    private static final long serialVersionUID = -7441213527564553063L;

    @ApiModelProperty(value = "体检类型数值")
    private String tjValue;

    @ApiModelProperty(value = "适用性别数值--用于调取不同类别的收费项")
    private String syxbValue;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "适用性别")
    private String syxb;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "分中心id")
    private List<String> fzxid;

    @ApiModelProperty(value = "是否科室加项：0.否 1.是")
    private Integer addItem;

}
