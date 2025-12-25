package com.center.medical.bean.event;

import com.center.medical.bean.param.CardConsumeParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:38
 * @description: 卡消费事件
 */
@Data
@AllArgsConstructor
public class CardConsumeEvent {

    @ApiModelProperty(value = "消费信息")
    private CardConsumeParam param;
}
