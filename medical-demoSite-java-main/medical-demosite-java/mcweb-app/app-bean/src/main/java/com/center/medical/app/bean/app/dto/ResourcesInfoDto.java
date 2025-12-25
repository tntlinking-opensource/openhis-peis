package com.center.medical.app.bean.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 路飞
 */
@Data
@ApiModel("文件上传成功地址信息")
public class ResourcesInfoDto {

    @ApiModelProperty(value = "服务器域名url", required = true)
    private String resourcesUrl;

    @ApiModelProperty(value = "文件上传的路径", required = true)
    private String filePath;
}
