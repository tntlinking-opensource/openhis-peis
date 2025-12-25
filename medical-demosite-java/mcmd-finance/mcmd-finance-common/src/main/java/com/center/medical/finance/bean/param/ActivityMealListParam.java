package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动套餐-列表参数
 */
@Data
public class ActivityMealListParam implements Serializable {
    private static final long serialVersionUID = -4974204890532314837L;

    @ApiModelProperty(value = "套餐id集合,最多对比三个")
    private List<String> ids;

    @ApiModelProperty(value = "今天的时间")
    private Date now;

}
