package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddItemsParam implements Serializable {

    private static final long serialVersionUID = 3286604903090276436L;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "套餐状态")
    private String combostate;

    @ApiModelProperty(value = "itemIds")
    private List<String> itemIds;
}
