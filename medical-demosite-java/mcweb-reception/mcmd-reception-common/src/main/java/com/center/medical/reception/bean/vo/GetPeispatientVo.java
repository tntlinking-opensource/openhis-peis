package com.center.medical.reception.bean.vo;

import com.center.medical.bean.model.Peispatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据体检号查询体检者信息
 */
@Data
public class GetPeispatientVo implements Serializable {
    private static final long serialVersionUID = 4182504641674891096L;

    @ApiModelProperty(value = "体检者信息")
    private Peispatient data;

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "加项未收  用于收费页面")
    private Double jxws;

    @ApiModelProperty(value = "加项退项未退 用于退费页面")
    private Double txwt;


    public GetPeispatientVo(Peispatient data, Integer total, Double jxws, Double txwt) {
        this.data = data;
        this.total = total;
        this.jxws = jxws;
        this.txwt = txwt;
    }


    public GetPeispatientVo() {
    }
}
