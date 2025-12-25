package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 根据订单号导入老数据到新系统中
 */
@Data
public class ImportDataParam implements Serializable {
    private static final long serialVersionUID = 488886943435374161L;

    @ApiModelProperty(value = "订单号集合,多个")
    private List<String> ddh;

}
