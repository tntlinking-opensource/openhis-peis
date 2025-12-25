package com.center.medical.abteilung.bean.vo;

import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.dto.DepartmentReportDto;
import com.center.medical.abteilung.bean.dto.DiagnosticHeadDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 普通预览科室报告 返回数据
 */
@Data
public class DiagnosticVo implements Serializable {
    private static final long serialVersionUID = 8015853715809165418L;

    @ApiModelProperty(value = "模板头部")
    private DiagnosticHeadDto head;

    @ApiModelProperty(value = "科室检查主表")
    private DepartmentReportDto sectionResultMain;

    @ApiModelProperty(value = "科室描述检查结果表")
    private List<Describe> describes;

    @ApiModelProperty(value = "职业问诊")
    private PeispatientConsultation peispatientConsultation;

    @ApiModelProperty(value = "职业史")
    private List<WzZys> wzZys;

    @ApiModelProperty(value = "职业病史")
    private List<WzZybs> wzZybs;

    @ApiModelProperty(value = "肺功能")
    private Lung lung;

    @ApiModelProperty(value = "数据报告格式，详见：com.center.medical.bean.enums.SjbggsType")
    private Integer sjbggs;
}
