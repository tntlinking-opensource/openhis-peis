package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 接收报告参数
 */
@Data
public class ReceivingReportsParam implements Serializable {
    private static final long serialVersionUID = 2992834057307532364L;

    @NotBlank(message = "授权码不能为空!")
    @ApiModelProperty(value = "授权码")
    private String authCode;

    @NotNull(message = "Pdf文件流不能为空!")
    @ApiModelProperty(value = "Pdf文件流")
    private MultipartFile file;

    @NotBlank(message = "体检号不能为空!")
    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
