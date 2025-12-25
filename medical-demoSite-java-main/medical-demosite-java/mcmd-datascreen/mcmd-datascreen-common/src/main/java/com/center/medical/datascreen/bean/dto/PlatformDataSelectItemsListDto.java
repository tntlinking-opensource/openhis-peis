package com.center.medical.datascreen.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动产品销售(项目)
 * @author xhp
 * @since 2023-06-05 15:34
 */
@Data
public class PlatformDataSelectItemsListDto {
    //业务类型名称
    private String name;
    //数量
    private int number;
    //总价
    private double amount;
    //业务类型id
    private String id;
}
