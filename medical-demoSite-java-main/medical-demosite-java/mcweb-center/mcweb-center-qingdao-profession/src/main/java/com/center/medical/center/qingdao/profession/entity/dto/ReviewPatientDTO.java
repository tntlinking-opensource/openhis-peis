package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;

@Data
public class ReviewPatientDTO {
    private String patientCode; //体检号
    private String prePatientCode; //上一次的复查体检号

}
