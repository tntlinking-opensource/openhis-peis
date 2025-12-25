package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppointReturnEditVo implements Serializable {

    private static final long serialVersionUID = -7289023447020063045L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "是否来检")
    private String isinspects;

    @ApiModelProperty(value = "预约来检时间")
    private Date inspectTimes;

    @ApiModelProperty(value = "回访时间")
    private Date visitTimes;

    @ApiModelProperty(value = "回访备注")
    private String memos;

}
