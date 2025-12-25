package com.center.medical.center.qingdao.profession.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SendPatientcode {
    @Excel(name="体检号")
    private String patientcode;
}
