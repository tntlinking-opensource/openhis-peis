package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通知结果参数
 */
@Data
public class NoticeParam implements Serializable {

    private static final long serialVersionUID = -8358028150598202845L;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "通知类型 (1.号码错 2.未接通 3.电话通知 4.短信通知 5.再通知)")
    private String type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "体检类型")
    private int diseaseHealth;

    @ApiModelProperty(value = "通知")
    private String notifyMemo;

}
