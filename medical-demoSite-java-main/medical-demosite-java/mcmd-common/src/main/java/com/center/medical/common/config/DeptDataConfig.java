package com.center.medical.common.config;

import lombok.Data;

import java.util.List;

/**
 * 科室数据范围配置
 */
@Data
public class DeptDataConfig {

    private List<RangeConfig> rangeData; //数据范围

}
