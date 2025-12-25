package com.center.medical.system.bean.dto;

import com.center.medical.system.bean.model.SysVersion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/4/16 17:42
 * @description: 当前版本信息
 */
@Data
public class CurVersionDto implements Serializable {
    private static final long serialVersionUID = 8968241274789281473L;

    @ApiModelProperty(value = "最新版本信息")
    private SysVersion version;

    @ApiModelProperty(value = "当前版本是否最新版本：0.否 1.是")
    private Integer lastVersion;

    @ApiModelProperty(value = "当前版本号")
    private String curVersion;

    @ApiModelProperty(value = "当前用户是否首次查看新版本：0.否 1.是")
    private Integer firstShow;
}
