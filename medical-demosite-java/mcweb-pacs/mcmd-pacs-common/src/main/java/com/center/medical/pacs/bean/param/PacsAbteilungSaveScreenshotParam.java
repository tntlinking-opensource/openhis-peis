package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-05-29 14:46
 */
@Data
public class PacsAbteilungSaveScreenshotParam implements Serializable {

    private static final long serialVersionUID = -4617029307618714432L;

    @ApiModelProperty(value = "base64", example = "data:image/png;base64,……", required = true)
    private String base64;

    @ApiModelProperty(value = "体检者收费项目id", required = true)
    @NotBlank
    private String feeitemId;

    @ApiModelProperty(value = "图片文件")
    private MultipartFile file;
}
