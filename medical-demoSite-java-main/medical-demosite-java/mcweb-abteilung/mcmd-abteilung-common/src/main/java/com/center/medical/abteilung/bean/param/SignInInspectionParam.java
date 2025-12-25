package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.SiFormdataDto;
import com.center.medical.abteilung.bean.dto.SiGriddataDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 预约、登记、保存右侧列表
 */
@Data
public class SignInInspectionParam implements Serializable {
    private static final long serialVersionUID = 8435535517148033639L;

    @JsonProperty("intReservation")
    @ApiModelProperty(value = "0 预约 1 登记 2 保存")
    private Integer intReservation;

    @JsonProperty("noticeJyk")
    @ApiModelProperty(value = "1通知检验科")
    private String noticeJyk;

    @ApiModelProperty(value = " 默认体检者照片")
    private String picture;

    @ApiModelProperty(value = " 保存更新数据")
    private SiFormdataDto formdata;

    @ApiModelProperty(value = " 保存表格数据")
    private List<SiGriddataDto> griddata;

    @JsonProperty("patientCode")
    @ApiModelProperty(value = " 体检码（saveOrUpdateItem方法使用）")
    private String patientCode;

    @ApiModelProperty(value = "签名地址")
    private String path;
}
