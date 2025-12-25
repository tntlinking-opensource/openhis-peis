package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记 修改体检者开单医师和备注 参数
 */
@Data
public class RCSaveEditParam implements Serializable {
    private static final long serialVersionUID = -1996446718613888365L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "电话")
    private String phone;

}
