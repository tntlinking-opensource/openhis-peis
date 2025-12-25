package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 第三方报告图片
 */
@Data
public class ThirdPartyImagesVo implements Serializable {
    private static final long serialVersionUID = 994088892353671792L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "图片路径")
    private String filePath;

    @ApiModelProperty(value = "pdf路径")
    private String pdfUrl;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

}
