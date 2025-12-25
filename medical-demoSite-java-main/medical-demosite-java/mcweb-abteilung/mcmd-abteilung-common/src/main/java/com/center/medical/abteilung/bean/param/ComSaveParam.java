package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.ComDataDto;
import com.center.medical.abteilung.bean.dto.ComFormDateDto;
import com.center.medical.abteilung.bean.dto.ComGridDataDto;
import com.center.medical.abteilung.bean.dto.PersonDataDto;
import com.center.medical.abteilung.bean.vo.RemindPatientVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 职业性问诊-审核保存参数
 */
@Data
public class ComSaveParam implements Serializable {
    private static final long serialVersionUID = 7970997966136731453L;


    @ApiModelProperty(value = "保存更新数据")
    private ComFormDateDto formdata;

    @ApiModelProperty(value = "保存更新数据")
    private List<ComGridDataDto> griddata;

    @ApiModelProperty(value = "保存更新数据")
    private List<ComDataDto> data;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "科室id")
    private PersonDataDto personData;

    @ApiModelProperty(value = "签名base64加密")
    private String url;

}
