package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据检查项目ID获取相对应的体征词
 */
@Data
public class FeatureListVo implements Serializable {
    private static final long serialVersionUID = -6576376093555893316L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体证词名称")
    private String tzcname;

    @ApiModelProperty(value = "是否阳性结果：0或null.否 1.是")
    private Integer isPositive;

    @ApiModelProperty(value = "体证词详细描述")
    private String bodyDetail;

    @ApiModelProperty(value = "体证词详细描述(职业病）")
    private String bodyDetailZy;

    @ApiModelProperty(value = "结论词ID")
    private String resultId;

    @ApiModelProperty(value = "结论词名称")
    private String name;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "是否选中：0.未选中 1.选中")
    private Integer isDefault;

    @ApiModelProperty(value = "是否进小结")
    private Integer isInSummary;

    @ApiModelProperty(value = "互斥分组（异组互斥）")
    private String otherMutex;

    @ApiModelProperty(value = "互斥分组（同组正整数编号互斥）")
    private Integer numMutex;

    @ApiModelProperty(value = "体征词显示顺序，1、2、3、4...")
    private Integer orderindex;
}
