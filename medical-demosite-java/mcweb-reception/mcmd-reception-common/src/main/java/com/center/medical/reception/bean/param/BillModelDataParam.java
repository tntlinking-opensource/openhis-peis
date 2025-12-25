package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导引单数据打印 参数
 */
@Data
public class BillModelDataParam implements Serializable {
    private static final long serialVersionUID = 3863201915763081667L;

    @ApiModelProperty(value = "体检者id集合")
    private List<String> ids;

    @ApiModelProperty(value = "模板id")
    private String model;

    @ApiModelProperty(value = "分中心id")
    private String id;
}
