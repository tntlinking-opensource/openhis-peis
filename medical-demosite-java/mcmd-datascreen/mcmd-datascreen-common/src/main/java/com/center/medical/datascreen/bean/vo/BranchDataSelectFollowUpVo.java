package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 回访率
 * @author xhp
 * @since 2023-06-13 14:40
 */
@Data
public class BranchDataSelectFollowUpVo {
    @ApiModelProperty(value = "危机值回访率",notes = "第一个值是回访率,第二个值是未回访率")
    private List<DatascreenBasePieVo> criticalValue;
    @ApiModelProperty(value = "团检顾客电话回访率",notes = "第一个值是回访率,第二个值是未回访率")
    private List<DatascreenBasePieVo> group;
    @ApiModelProperty(value = "个检顾客电话回访率",notes = "第一个值是回访率,第二个值是未回访率")
    private List<DatascreenBasePieVo> person;
    @ApiModelProperty(value = "现场满意度调查",notes = "第一个值是满意度,第二个值是不满意度")
    private List<DatascreenBasePieVo> satisfaction;
}
