package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-11-24 16:45
 * @description: 档案合并参数
 */
@Data
public class ArchiveMergeParam implements Serializable {
    private static final long serialVersionUID = 1503080991587187259L;

    @ApiModelProperty("选择合并的档案id集合")
    private List<String> ids;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String patientname;
}
