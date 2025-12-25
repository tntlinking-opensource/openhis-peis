package com.center.medical.common.config;

import lombok.Data;

/**
 * 骨密度范围
 */
@Data
public class RangeConfig {

    private String deptNo; //科室编号
    private String deptName; //科室名称
    private Double low; //低值
    private Double high; //高值
}
