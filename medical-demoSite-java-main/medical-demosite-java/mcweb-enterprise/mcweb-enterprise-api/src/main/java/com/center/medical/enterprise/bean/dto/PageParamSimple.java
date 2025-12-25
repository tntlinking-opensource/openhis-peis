package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 分页参数
 */
@Data
public class PageParamSimple {


    /**
     * 每页显示条数，默认 10
     */
    @ApiParam(value = "每页大小，默认10", required = false, defaultValue = "10")
    private long size = 10;

    /**
     * 当前页
     */
    @ApiParam(value = "当前页，默认1", required = false, defaultValue = "0")
    private long current = 0;

}
