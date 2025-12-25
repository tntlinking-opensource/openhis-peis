package com.center.medical.olddata.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ImportTjPeopleParam implements Serializable {
    private static final long serialVersionUID = 1536404262786165643L;

    @ApiModelProperty(value = "订单号集合")
    private List<String> ddhs;

    @ApiModelProperty(value = "开始时间")
    private String startTime;


    @ApiModelProperty(value = "结束时间")
    private String endTime;


    public ImportTjPeopleParam(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ImportTjPeopleParam() {
    }
}
