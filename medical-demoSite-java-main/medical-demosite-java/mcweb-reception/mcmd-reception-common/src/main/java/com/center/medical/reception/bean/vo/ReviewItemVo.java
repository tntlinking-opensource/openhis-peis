package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-23 10:22
 * @description: 复查记录关联的收费项目数据
 */
@Data
public class ReviewItemVo implements Serializable {
    private static final long serialVersionUID = -3916789932448619772L;

    @ApiModelProperty(value = "项目名称")
    private String xm;

    @ApiModelProperty(value = "科室名称")
    private String ks;

}
