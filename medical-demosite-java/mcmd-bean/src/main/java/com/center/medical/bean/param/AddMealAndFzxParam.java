package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddMealAndFzxParam implements Serializable {

    @ApiModelProperty("套餐ids")
    private List<String> ids;

    @ApiModelProperty("分中心")
    private List<String> fzxs;
}
