package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveSignAllParam implements Serializable {
    private static final long serialVersionUID = 6459788401073863357L;



    @ApiModelProperty(value = "参数")
    private List<SaveSignParam> SaveSignParams;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "名称")
    private String name;

}
