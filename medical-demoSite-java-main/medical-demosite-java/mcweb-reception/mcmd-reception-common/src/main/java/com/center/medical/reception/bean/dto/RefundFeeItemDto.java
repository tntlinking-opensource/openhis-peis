package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-03 8:09
 * @description: 前台-登记管理-新增登记提交的登记信息
 */
@Data
@ApiModel(value = "退项信息", description = "前台-登记管理-退项提交的信息")
public class RefundFeeItemDto implements Serializable {

    private static final long serialVersionUID = 1904220814613806065L;

    @ApiModelProperty(value = "体检号", position = 1)
    private String patientcode;

    @ApiModelProperty(value = "姓名", position = 2)
    private String patientname;

    @ApiModelProperty(value = "版本", position = 4)
    private Long version;

    @ApiModelProperty(value = "可选数量", position = 5)
    private Integer bxcount;

    @ApiModelProperty(value = "收费项目列表", position = 6)
    private List<ItemsDto> itemList;

}
