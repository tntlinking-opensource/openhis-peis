package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xhp
 * @since 2023-03-22 14:31
 */
@Data
public class PacsAbteilungSaveParam implements Serializable {
    private static final long serialVersionUID = 7638537603756906354L;

    @ApiModelProperty(value = "体征词列表")
    @Valid
    private List<PacsAbteilungSaveSignParam> jlcdata;

    @ApiModelProperty(value = "科室编号", required = true)
    @NotBlank
    private String deptNo;

    @ApiModelProperty(value = "体检者收费项目id", required = true)
    @NotBlank
    private String feeitemId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "小结")
    private String conclusions;

    @ApiModelProperty(value = "检查人用户名", required = true)
    @NotBlank
    private String rummagerName;

    @ApiModelProperty(value = "1保存  2审核", required = true)
    private int type;

    @ApiModelProperty(value = "检查人id", required = true)
    @NotBlank
    private String rummagerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "检查时间", required = true)
    @NotNull
    private Date rummagerTime;

    @ApiModelProperty(value = "审核人id", required = true)
    @NotBlank
    private String writeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间", required = true)
    @NotNull
    private Date writeTime;
    
    @ApiModelProperty(value = "图片")
    @Valid
    private List<PacsAbteilungSaveImgParam> imgs;
}
