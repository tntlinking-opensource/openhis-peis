package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Dicom图片接收失败查询参数
 *
 * @author 路飞
 * @since 2023-03-16 8:47
 */
@Data
public class FailedDicomParam implements Serializable {
    private static final long serialVersionUID = -3911605036522408032L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
