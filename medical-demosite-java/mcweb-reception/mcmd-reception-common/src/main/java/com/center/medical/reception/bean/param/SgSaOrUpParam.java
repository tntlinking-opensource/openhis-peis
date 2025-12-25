package com.center.medical.reception.bean.param;

import com.center.medical.reception.bean.dto.SgGriddataDto;
import com.center.medical.reception.bean.dto.SgItemGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 外送管理-外送结果上传保存
 */
@Data
public class SgSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -7620234918917156836L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "添加的图片")
    private String addImg;

    @ApiModelProperty(value = "删除的图片")
    private String delImg;

    @ApiModelProperty(value = "formdataId")
    private String formdataId;

    @ApiModelProperty(value = "保存表格数据")
    private List<SgGriddataDto> griddata;

    @ApiModelProperty(value = "保存表格数据")
    private List<SgItemGriddataDto> itemGriddata;


}
