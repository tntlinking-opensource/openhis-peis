package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 体检项目分类小结
 */
@Data
public class OhItemClassResultVo implements Serializable {
    private static final long serialVersionUID = 3859244613521474975L;

    @ApiModelProperty(value = "项目分类名称")
    private String itemClassName;

    @ApiModelProperty(value = "体检项目结果")
    private List<OhItemResultDto> ohItemResult;
}
