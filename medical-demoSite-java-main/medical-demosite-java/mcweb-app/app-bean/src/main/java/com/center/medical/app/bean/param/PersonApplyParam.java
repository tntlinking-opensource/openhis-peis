package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个检提交预约申请 参数
 */
@Data
public class PersonApplyParam implements Serializable {
    private static final long serialVersionUID = -8230402752216801507L;

    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "预约时间", required = true, position = 3)
    private String reservationDate;

    @ApiModelProperty(value = "预约时间段ID", required = true, position = 4)
    private String timeId;

}
