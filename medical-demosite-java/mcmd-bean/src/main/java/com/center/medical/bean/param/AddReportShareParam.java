package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 报告分享添加及修改参数
 */
@Data
public class AddReportShareParam implements Serializable {
    private static final long serialVersionUID = 6988487128019875765L;

    @ApiModelProperty("体检号集合，多个以英文逗号（,）隔开")
    private List<String> patientcodes;

}
