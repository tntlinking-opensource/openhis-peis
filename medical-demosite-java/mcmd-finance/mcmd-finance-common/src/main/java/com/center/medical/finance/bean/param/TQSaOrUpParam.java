package com.center.medical.finance.bean.param;

import com.center.medical.finance.bean.dto.TQFormDataDto;
import com.center.medical.finance.bean.dto.TQGridDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 记账管理-记账结算保存参数
 */
@Data
public class TQSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 7574341097594179773L;

    @ApiModelProperty(value = "上方体检者数据")
    private TQFormDataDto formdata;

    @ApiModelProperty(value = "上方体检者数据")
    private List<TQGridDataDto> griddata;

}
