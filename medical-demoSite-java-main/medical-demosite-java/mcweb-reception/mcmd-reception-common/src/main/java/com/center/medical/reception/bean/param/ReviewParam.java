package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-11-23 8:58
 * @description: 复查数据查询参数
 */
@Data
public class ReviewParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 6292760023632978442L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "复查时间起")
    private Date dateFrom;

    @ApiModelProperty(value = "复查时间止")
    private Date dateTo;

    @ApiModelProperty(value = "复查状态")
    private Integer callbackStation;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

}
