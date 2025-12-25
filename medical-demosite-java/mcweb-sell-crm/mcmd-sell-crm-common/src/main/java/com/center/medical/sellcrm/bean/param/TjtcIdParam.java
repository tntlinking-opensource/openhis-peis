package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存订单-体检套餐参数
 */
@Data
public class TjtcIdParam implements Serializable {
    private static final long serialVersionUID = 2815186105943947972L;

    @ApiModelProperty(value = "状态，removed删除，modified修改,added添加")
    private String state;

    @ApiModelProperty(value = "最小套餐标识：0:普通套餐 1最小套餐")
    private String combostate;

    @ApiModelProperty(value = "套餐id")
    private String id;

    @ApiModelProperty(value = "检查类型，0.健康类 1.职业类 2.综合类 5.入职类 6.疫苗类 7.其他类")
    private String idExamclass;

    @ApiModelProperty(value = "订单与套餐关联表id")
    private String oacid;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;
}
