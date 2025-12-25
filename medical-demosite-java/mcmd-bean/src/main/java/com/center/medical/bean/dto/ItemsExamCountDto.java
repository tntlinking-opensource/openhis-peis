package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-18 19:37
 * @description: 收费项目体检次数
 */
@Data
public class ItemsExamCountDto implements Serializable {
    private static final long serialVersionUID = 6231052038087642449L;

    @ApiModelProperty(value = "体检者收费项目序号")
    private String idPatientfeeitem;

    @ApiModelProperty(value = "体检次数")
    private Integer count;

}
