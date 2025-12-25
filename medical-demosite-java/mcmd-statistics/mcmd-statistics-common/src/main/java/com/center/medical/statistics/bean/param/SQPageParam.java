package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康检查拒检补检人员名单 分页参数
 */
@Data
public class SQPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -1152658601391927908L;

    @ApiModelProperty(value = "医院代码")
    private String cid;

    @ApiModelProperty(value = "团体名称")
    private String idOrg;
}
