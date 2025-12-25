package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-03-22 14:33
 */
@Data
public class PacsAbteilungSaveSignParam implements Serializable {
    private static final long serialVersionUID = 8431313491622101588L;
    
    @ApiModelProperty(value = "pacs体征词id", required = true)
    @NotBlank
    private String tzcid;
}
