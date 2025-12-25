package com.center.medical.olddata.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导入老系统收费项目参数
 */
@Data
public class ImportItemsParam implements Serializable {
    private static final long serialVersionUID = -8290094552152890885L;


    @ApiModelProperty(value = "ids,多个以逗号分割")
    private List<String> ids;
}
