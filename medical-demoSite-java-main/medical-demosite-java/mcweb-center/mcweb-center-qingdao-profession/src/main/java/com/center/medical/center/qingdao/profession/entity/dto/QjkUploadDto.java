package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class QjkUploadDto implements Serializable {
    private static final long serialVersionUID = -6240649051072413089L;

    private String startDate;

    private String endDate;

    private String patientcode;
}
