package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 批量设置数据参数
 */
@Data
public class BatchSettingDataParam implements Serializable {
    private static final long serialVersionUID = -2480890892390233819L;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "批量设置数据")
    private List<BatchSettingsParam> settingsParam;

}
