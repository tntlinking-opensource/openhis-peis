package com.center.medical.machine.bean.dto;

import com.center.medical.bean.model.Peispatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class PeispatientDto extends Peispatient implements Serializable {

    private static final long serialVersionUID = -3225129463603043434L;

    @ApiModelProperty(value = "类型:团检，个人，复检，补检，入职")
    private String type;

    @ApiModelProperty(value = "decimal")
    private String decimal;

    @ApiModelProperty(value = "线上")
    private Integer online;

}
