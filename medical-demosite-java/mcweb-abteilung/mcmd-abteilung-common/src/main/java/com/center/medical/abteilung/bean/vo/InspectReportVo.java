package com.center.medical.abteilung.bean.vo;

import com.center.medical.abteilung.bean.dto.DiagnosticHeadDto;
import com.center.medical.reception.bean.vo.GetInspectReportVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检验科科室报告 返回数据
 */
@Data
public class InspectReportVo implements Serializable {
    private static final long serialVersionUID = -3092977432999787442L;

    @ApiModelProperty(value = "头部数据")
    private DiagnosticHeadDto head;

    @ApiModelProperty(value = "外送结果图片")
    private List<String> testPic;

    @ApiModelProperty(value = "小结")
    private String conclusions;

    @ApiModelProperty(value = "lis结果")
    private List<GetInspectReportVo> peispatientexamitems;

    @ApiModelProperty(value = "数据报告格式，详见：com.center.medical.bean.enums.SjbggsType")
    private Integer sjbggs;
}
