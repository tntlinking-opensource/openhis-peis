package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-02-24 9:52
 * @description: 版本更新执行生成各中心更新服务记录参数
 */
@Data
public class SysVersionExecuteParam {

    @ApiModelProperty(value = "要执行的中心ID集合，多个以英文逗号（,）隔开")
    private List<String> branchIds;
    
    @ApiModelProperty(value = "版本ID")
    private Integer versionId;
}
