package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查看短信数据参数
 */
@Data
public class SmssendParam implements Serializable {
    private static final long serialVersionUID = 3274500894200466677L;

    @ApiModelProperty(value = "id集合")
    private List<String> ids;

    @ApiModelProperty(value = "体检名称集合")
    private List<String> patientcode;

    @ApiModelProperty(value = "体检名称集合")
    private List<String> patientname;
}
