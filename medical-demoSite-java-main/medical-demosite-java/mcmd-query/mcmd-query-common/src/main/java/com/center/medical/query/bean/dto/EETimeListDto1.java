package com.center.medical.query.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统计某段时间所有体检者
 */
@Data
public class EETimeListDto1 implements Serializable {

    private static final long serialVersionUID = -1147704547991072408L;

    @Excel(name = "原价合计")
    @ApiModelProperty(value = "yjhj")
    private String yjhj;

    @Excel(name = "加项人次")
    @ApiModelProperty(value = "jxrc")
    private String jxrc;

    @Excel(name = "加项费用")
    @ApiModelProperty(value = "jxfy")
    private String jxfy;
}
