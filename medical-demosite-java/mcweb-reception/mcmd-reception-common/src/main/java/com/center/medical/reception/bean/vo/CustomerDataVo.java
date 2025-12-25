package com.center.medical.reception.bean.vo;

import com.center.medical.bean.model.Peispatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-16 15:47
 * @description: 体检者与收费项目信息
 */
@Data
public class CustomerDataVo implements Serializable {
    private static final long serialVersionUID = 484916342118917471L;

    @ApiModelProperty(value = "体检者信息")
    private Peispatient peispatient;

    @ApiModelProperty(value = "接害因素")
    private String jhysn;

    @ApiModelProperty(value = "右侧收费项目")
    private List<PeispatientfeeitemVo> examfeeitemData;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "退费金额")
    private Double tMoney;

    @ApiModelProperty(value = "版本号")
    private Long version;
}
