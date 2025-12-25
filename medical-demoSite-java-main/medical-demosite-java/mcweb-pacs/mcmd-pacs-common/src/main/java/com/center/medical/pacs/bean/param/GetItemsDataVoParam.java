package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目表格数据 参数
 */
@Data
public class GetItemsDataVoParam implements Serializable {
    private static final long serialVersionUID = -3549935360480298248L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室id")
    private String ksID;

}
