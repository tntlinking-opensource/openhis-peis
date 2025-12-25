package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 获取可预约vip人数 参数
 */
@Data
public class GetReVipNumberParam implements Serializable {
    private static final long serialVersionUID = -6971311574162141575L;

    @ApiModelProperty(value = "公司id")
    private String idOrg;

    @ApiModelProperty(value = "预约日期", required = true)
    @NotNull(message = "请先选择起始日期!")
    private Date startDate;

    @ApiModelProperty(value = "预约日期", required = true)
    @NotNull(message = "请先选择截至日期!")
    private Date endDate;

}
