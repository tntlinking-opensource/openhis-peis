package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 反审核
 *
 * @author xhp
 * @since 2023-03-23 13:57
 */
@Data
public class PacsAbteilungReverseParam implements Serializable {
    private static final long serialVersionUID = -691646840970070613L;
    
    @ApiModelProperty(value = "体检者收费项目id")
    @NotBlank
    private String feeitemId;
}
