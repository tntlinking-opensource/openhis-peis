package com.center.medical.bean.event;

import com.center.medical.bean.param.OldFamilyConParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 家庭卡消费参数
 */
@Data
@AllArgsConstructor
public class FamilyConsumptionEvent {

    @ApiModelProperty(value = "消费信息")
    private OldFamilyConParam param;

}
