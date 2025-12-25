package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检卡办理分页参数
 */
@Data
public class CHPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3399848206266004756L;

    @ApiModelProperty(value = "开始卡号")
    private String startNo;

    @ApiModelProperty(value = "结束卡号")
    private String endNo;

    @ApiModelProperty(value = "体检卡类型")
    private String typeId;

    @ApiModelProperty(value = "售卡人")
    private String sellId;

    @ApiModelProperty(value = "使用开始时间")
    private Date startUseTime;

    @ApiModelProperty(value = "使用结束时间")
    private Date endUseTime;

    @ApiModelProperty(value = "售卡状态")
    private Integer sellStatus;

    @ApiModelProperty(value = "套餐名称或输入码")
    private Integer tcName;
}
