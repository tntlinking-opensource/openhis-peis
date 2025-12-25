package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-03-29 13:52
 */
@Data
public class PacsBasexamltemSignVo {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体征词名称")
    private String name;

    @ApiModelProperty(value = "结论词id")
    private String resultId;

    @ApiModelProperty(value = "结论词名称")
    private String resultName;

    @ApiModelProperty(value = "体证词详细描述")
    private String bodyDetail;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "是否阳性结果：0或null.否 1.是")
    private Integer isPositive;

    @ApiModelProperty(value = "是否默认选中：0.不选中 1.选中")
    private Integer isDefault;

    @ApiModelProperty(value = "体征词显示顺序")
    private Integer orderindex;

}
