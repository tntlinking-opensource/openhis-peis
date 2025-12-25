package com.center.medical.pacs.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数值结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumResultsDto implements Serializable {
    private static final long serialVersionUID = 117234837610267921L;

    //Hz数、500、1000、2000等
    private String hz;

    //数值结果
    private Double result;

    //1左耳、2右耳
    private String ears;

    //1气导 3骨导
    private String way;

    //时间戳
    private String t;

}
