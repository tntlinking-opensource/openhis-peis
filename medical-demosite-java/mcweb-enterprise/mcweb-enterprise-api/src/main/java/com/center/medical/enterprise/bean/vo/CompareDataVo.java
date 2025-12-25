package com.center.medical.enterprise.bean.vo;

import com.center.medical.enterprise.bean.dto.KsResultsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 对比报告返回数据
 */
@Data
public class CompareDataVo implements Serializable {
    private static final long serialVersionUID = -1641614482909834478L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室数据")
    private List<KsResultsDto> ksResultsDtoList;

    public CompareDataVo(String patientcode, List<KsResultsDto> ksResultsDtoList) {
        this.patientcode = patientcode;
        this.ksResultsDtoList = ksResultsDtoList;
    }

    public CompareDataVo() {
    }
}
