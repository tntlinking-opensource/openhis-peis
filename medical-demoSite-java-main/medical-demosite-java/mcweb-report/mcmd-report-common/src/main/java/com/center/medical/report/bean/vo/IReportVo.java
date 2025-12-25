package com.center.medical.report.bean.vo;

import com.center.medical.report.bean.dto.IReportDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 速成或生成检验报告 返回数据
 */
@Data
public class IReportVo implements Serializable {
    private static final long serialVersionUID = -2104594231605380480L;

    @ApiModelProperty(value = "模板头")
    private Map<String,Object> head;

    @ApiModelProperty(value = "科室数据")
    private List<IReportDto> ksList;

    @ApiModelProperty(value = "拒检项目")
    private Map<String,Object> jjxm;

    @ApiModelProperty(value = "尾部图片")
    private List<Map> endPicture;

    @ApiModelProperty(value = "职业模板尾")
    private Map<String,Object> end;

    @ApiModelProperty(value = "隐私报告(单独一页,类似于科室报告)")
    private PrivateReportVo ysbg;

    @ApiModelProperty(value = "第三方报告")
    private List<String> threeReport;
}
