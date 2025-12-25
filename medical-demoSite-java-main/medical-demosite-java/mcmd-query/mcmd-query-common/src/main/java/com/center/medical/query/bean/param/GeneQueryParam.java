package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新产品数据查询 分页参数
 */
@Data
public class GeneQueryParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5274902511782899762L;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "分中心集合")
    private List<String> branchIds;
}

