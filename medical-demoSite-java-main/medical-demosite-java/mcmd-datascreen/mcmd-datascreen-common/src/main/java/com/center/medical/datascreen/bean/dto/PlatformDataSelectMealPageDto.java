package com.center.medical.datascreen.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 体检套餐概况
 * @author xhp
 * @since 2023-06-02 14:13
 */
@Data
public class PlatformDataSelectMealPageDto {
    //套餐名称
    private String examsuiteName;
    //销量
    private int sales;
    //套餐id
    private String id;
}
