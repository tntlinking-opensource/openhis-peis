package com.center.medical.bean.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:38
 * @description: 分中心新增或者更新事件
 */
@Data
@AllArgsConstructor
public class SaOrUpBranchEvent {

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "简码，不能重复")
    private String jm;

    @ApiModelProperty(value = "拼音简码（用于会员卡卡号）")
    private String pyjm;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    @ApiModelProperty(value = "操作标识：1新增 2更新")
    private Integer flag;
}
