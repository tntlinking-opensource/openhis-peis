package com.center.medical.sellcrm.bean.param;

import com.center.medical.sellcrm.bean.dto.MealJsonDto;
import com.center.medical.sellcrm.bean.dto.MealJsonSfxmDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增套餐保存参数
 */
@Data
public class MealSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 8792336497090948750L;

    @ApiModelProperty(value = "上方套餐信息")
    private MealJsonDto json;

    @ApiModelProperty(value = "下方收费项目")
    private List<MealJsonSfxmDto> jsonSfxm;
}
