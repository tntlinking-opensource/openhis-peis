package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalHistoryDto implements Serializable {
    private static final long serialVersionUID = -7398675426757312592L;

    @ApiModelProperty(value = "目前吸烟情况代码")
    private String smokingStatus;

    @ApiModelProperty(value = "吸烟史-年")
    private Integer personalHistoryYear;

    @ApiModelProperty(value = "吸烟史-月")
    private Integer personalHistoryMonth;

    @ApiModelProperty(value = "平均每天吸烟量（支）")
    private Integer dailySmokingVolum;

}
