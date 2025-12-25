package com.center.medical.pacslis.bean.param;

import com.center.medical.pacslis.bean.dto.PrFormdataDto;
import com.center.medical.pacslis.bean.dto.PrGriddataDto;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登记保存参数
 */
@Data
public class PrSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 7890674814765868099L;

    @ApiModelProperty(value = "保存更新数据")
    private PrFormdataDto formData;


    @ApiModelProperty(value = "保存更新数据")
    private List<PrGriddataDto> Griddata;


    @ApiModelProperty(value = "0 预约 1 登记 2 保存")
    private int intReservation;

}
