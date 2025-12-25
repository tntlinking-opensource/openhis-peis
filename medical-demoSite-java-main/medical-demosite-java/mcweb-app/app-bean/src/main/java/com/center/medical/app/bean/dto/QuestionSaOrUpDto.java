package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 健康问卷 添加或更新 返回数据
 */
@Data
public class QuestionSaOrUpDto implements Serializable {
    private static final long serialVersionUID = -5946320451190288054L;

    @ApiModelProperty(value = "id")
    private String id;
}
