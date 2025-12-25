package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 订单导入名单参数
 */
@Data
public class BeachSave2Param implements Serializable {
    private static final long serialVersionUID = -4863511984220978150L;

    @ApiModelProperty(value = "名单文件（excl）")
    private MultipartFile file;

    @ApiModelProperty(value = "订单ID")
    private String id;

    @ApiModelProperty(value = "模板类型：0线下职业  1线下健康  2人员名单导入 3线上健康  4线上职业")
    private Integer modelType;
}
