package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 问卷保存参数
 */
@Data
public class AnswerParam implements Serializable {
    private static final long serialVersionUID = 2509382045141718721L;

    @ApiModelProperty(value = "集合")
    private Map<String,Object> map;


}
