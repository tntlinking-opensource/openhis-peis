package com.center.medical.olddata.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddUserFzxParam implements Serializable {
    private static final long serialVersionUID = 5721573061909243053L;

    @ApiModelProperty(value = "userNo，多个以逗号隔开")
    private List<String> userNos;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;
}
