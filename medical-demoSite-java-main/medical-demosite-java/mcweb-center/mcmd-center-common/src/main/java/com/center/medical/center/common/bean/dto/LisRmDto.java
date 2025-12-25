package com.center.medical.center.common.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 瑞美lis数据库视图结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LisRmDto {
    private String  technician;
    private Date testDate;
    private String  reportDesc;
    private String  reportStatus;
    private String  refRange;
    private String  unit;
    private String  sampleno;
    private String  rechkUsername;
    private String  reportUser;
    private String examCode;
}
