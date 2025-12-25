package com.center.medical.reception.bean.dto;

import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.Peispatientfeeitem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 拉取线上体检者数据 返回数据
 */
@Data
public class PullOnlineDataDto implements Serializable {
    private static final long serialVersionUID = 4766704121163759666L;

    @ApiModelProperty(value = "体检者收费项目")
    private List<Peispatientfeeitem> peispatientfeeitemList;

    @ApiModelProperty(value = "体检者收费主表")
    private PeispatientChargeMain peispatientChargeMain;
}
