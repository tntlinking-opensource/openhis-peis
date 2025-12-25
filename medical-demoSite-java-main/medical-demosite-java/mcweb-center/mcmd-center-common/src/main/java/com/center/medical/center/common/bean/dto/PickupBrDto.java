package com.center.medical.center.common.bean.dto;

import com.center.medical.center.common.bean.model.IntPatient;
import com.center.medical.center.common.bean.model.IntPatientFeeItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PickupBrDto implements Serializable {
    private static final long serialVersionUID = 5166318168089035201L;

    private IntPatient intPatient;//中间库体检者表
    private List<IntPatientFeeItem> IntPatientFeeItems;//中间库体检者收费项目表


    public PickupBrDto(IntPatient intPatient, List<IntPatientFeeItem> intPatientFeeItems) {
        this.intPatient = intPatient;
        this.IntPatientFeeItems = intPatientFeeItems;
    }

    public PickupBrDto() {
    }
}
