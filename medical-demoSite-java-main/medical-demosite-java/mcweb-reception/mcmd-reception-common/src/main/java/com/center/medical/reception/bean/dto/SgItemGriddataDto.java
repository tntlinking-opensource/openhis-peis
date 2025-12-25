package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送管理-外送结果上传 保存项目数据
 */
@Data
public class SgItemGriddataDto implements Serializable {

    private static final long serialVersionUID = -6968440123924090036L;

    @ApiModelProperty(value = "状态，removed删除，modified修改,added添加")
    private String state;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目id")
    private String idCharge;

    @ApiModelProperty(value = "检查项目打印名称")
    private String itemName;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "科室名称")
    private String ksName;
}
