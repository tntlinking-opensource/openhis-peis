package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 获取所有收费项目返回数据
 */
@Data
public class AllItemsVo implements Serializable {
    private static final long serialVersionUID = -6912562659128256249L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

}
