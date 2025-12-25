package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2023-02-24 9:52
 * @description: 查询参数
 */
@Data
public class SysVersionServiceParam {

    @ApiModelProperty(value = "系统服务ID（关联数据表sys_service_type的ID）")
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "版本ID(关联sys_version_info的ID)")
    private Integer versionId;

    @ApiModelProperty(value = "状态：0.正常 1.停用")
    private Integer status;

}
