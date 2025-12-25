package com.center.medical.abteilung.bean.vo;

import com.center.medical.abteilung.bean.dto.DepartmentReportDto;
import com.center.medical.abteilung.bean.dto.DiagnosticHeadDto;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Tjreg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 图片科室预览报告 返回数据
 */
@Data
public class PicReportVo implements Serializable {
    private static final long serialVersionUID = 5193593362330837084L;

    @ApiModelProperty(value = "头部数据")
    private DiagnosticHeadDto head;

    @ApiModelProperty(value = "附件-图片")
    private List<Attachment> attachment;

    @ApiModelProperty(value = "电测听")
    private ElectroAudiometer electroAudiometer;

    @ApiModelProperty(value = "一般检查")
    private Tjreg tjreg;

    @ApiModelProperty(value = "科室检查主表")
    private DepartmentReportDto sectionResultMain;

    @ApiModelProperty(value = "数据报告格式，详见：com.center.medical.bean.enums.SjbggsType")
    private Integer sjbggs;

    @ApiModelProperty(value = "电测听职业")
    private List<Map> electroAudiometerWork;
}
