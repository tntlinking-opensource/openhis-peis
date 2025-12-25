package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2023-02-24 9:52
 * @description: 版本查询参数
 */
@Data
public class SysVersionParam {

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "获取最新的版本信息数量")
    private Integer count;

    @ApiModelProperty(value = "版本状态：-1失效 0.待更新 1.已更新部分 2.全部已更新")
    private Integer status;

}
