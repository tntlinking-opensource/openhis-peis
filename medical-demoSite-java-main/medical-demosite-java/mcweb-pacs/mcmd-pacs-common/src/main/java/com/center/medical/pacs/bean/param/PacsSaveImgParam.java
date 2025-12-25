package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存图片 参数
 */
@Data
public class PacsSaveImgParam implements Serializable {
    private static final long serialVersionUID = -439996932883105472L;

    @ApiModelProperty(value = "图片base64")
    private String base64;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "PACS体检者收费项目ID")
    private String feeitemId;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "type")
    private String type;


}
