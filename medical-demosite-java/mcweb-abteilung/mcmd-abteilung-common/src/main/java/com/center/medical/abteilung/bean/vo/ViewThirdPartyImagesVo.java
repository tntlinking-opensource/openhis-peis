package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查看第三方图片返回数据
 */
@Data
public class ViewThirdPartyImagesVo implements Serializable {
    private static final long serialVersionUID = -5090016109855780963L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "图片路径")
    private List<String> filePath;

    @ApiModelProperty(value = "pdf路径")
    private String pdfUrl;


}
