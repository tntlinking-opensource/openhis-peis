package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-03-22 15:30
 */
@Data
public class PacsAbteilungSaveImgParam implements Serializable {
    private static final long serialVersionUID = 7806018879715045339L;

    @ApiModelProperty(value = "1进报告  0不进报告", required = true)
    private int inReport;
    
    @ApiModelProperty(value = "图片id", required = true)
    @NotBlank
    private String id;
}
