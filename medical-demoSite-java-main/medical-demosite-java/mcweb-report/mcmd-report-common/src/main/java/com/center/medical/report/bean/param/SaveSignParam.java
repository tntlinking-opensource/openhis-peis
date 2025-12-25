package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存综述 结论 建议 职业和健康共用
 */
@Data
public class SaveSignParam implements Serializable {

    private static final long serialVersionUID = 4122136034712610234L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "状态 added添加，modified修改，removed删除")
    private String state;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "标志：0不出现,1出现")
    private String see;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "科室名称")
    private String division;

}
