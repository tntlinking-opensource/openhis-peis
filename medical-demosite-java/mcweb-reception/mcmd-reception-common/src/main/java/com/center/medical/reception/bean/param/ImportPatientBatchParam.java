package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 路飞
 * @date: 2023-04-11 14:10
 * @description: 备单管理导入名单参数
 */
@Data
public class ImportPatientBatchParam {

    @ApiModelProperty(value = "名单文件（excl）")
    private MultipartFile file;

    @ApiModelProperty(value = "分组ID")
    private String id;

    @ApiModelProperty(value = "模板类型：0线下职业  1线下健康  2人员名单导入 3线上健康  4线上职业")
    private Integer modelType;
}
