package com.center.medical.sellcrm.bean.param;

import com.center.medical.sellcrm.bean.dto.COFormDataDto;
import com.center.medical.sellcrm.bean.dto.COGridDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 导入名单-保存
 */
@Data
public class SaOrUpNameListParam implements Serializable {
    private static final long serialVersionUID = -1077021810305215688L;

    @ApiModelProperty(value = "订单信息")
    private COFormDataDto coFormData;

    @ApiModelProperty(value = "体检者信息")
    private List<COGridDataDto> coGridDataDtos;

}
