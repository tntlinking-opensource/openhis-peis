package com.center.medical.datascreen.bean.dto;

import lombok.Data;

/**
 * @author xhp
 * @since 2023-06-02 10:24
 */
@Data
public class PlatformDataPersonPatientPageDto {
    //体检套餐
    private String examsuiteName;
    //体检人
    private String patientname;
    //金额
    private Double amount;
    //销售员
    private String doctorapply;
    //体检号
    private String patientcode;
}
