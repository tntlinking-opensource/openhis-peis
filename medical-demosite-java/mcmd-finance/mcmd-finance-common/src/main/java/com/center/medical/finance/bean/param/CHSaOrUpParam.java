package com.center.medical.finance.bean.param;

import com.center.medical.finance.bean.dto.CHFormDataDto;
import com.center.medical.finance.bean.dto.CHGridDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 体检卡管理-体检卡办理保存或修改 参数
 */
@Data
public class CHSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -7157173610238067147L;

    @ApiModelProperty(value = "体检卡数据")
    private CHFormDataDto formdata;
    
    @ApiModelProperty(value = "卡办理收款方式")
    private List<CHGridDataDto> griddata;
}
