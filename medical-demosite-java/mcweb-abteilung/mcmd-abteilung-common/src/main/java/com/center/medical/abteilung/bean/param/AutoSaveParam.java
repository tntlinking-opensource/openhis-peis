package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.ASaveDataDto;
import com.center.medical.abteilung.bean.dto.ComFormDateDto;
import com.center.medical.abteilung.bean.dto.JsonDataDto;
import com.center.medical.abteilung.bean.dto.saOrUpJlcDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 小结参数
 */
@Data
public class AutoSaveParam implements Serializable {
    private static final long serialVersionUID = -1415779703532392934L;

    @ApiModelProperty(value = "体征词、检查描述")
    private List<JsonDataDto> jsondata;

    @ApiModelProperty(value = "保存表格数据")
    private List<saOrUpJlcDto> griddata;

    @ApiModelProperty(value = "小结")
    private String xjdata;

    @ApiModelProperty(value = "检查人")
    private String jcr;

    @ApiModelProperty(value = "检查时间")
    private Date jcsj;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "录入时间")
    private Date lrsj;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "危急值级别")
    private Integer zdwjz;

    @ApiModelProperty(value = "zdwjzjb")
    private String zdwjzjb;

    @ApiModelProperty(value = "data")
    private List<ASaveDataDto> data;


}
