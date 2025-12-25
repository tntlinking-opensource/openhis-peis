package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团检加/弃项分页查询参数
 */
@Data
public class ItemListParam implements Serializable {
    private static final long serialVersionUID = -7410589884410220293L;

    @ApiModelProperty(value = "客户单位名称cid")
    private String khdwmcid;

    @ApiModelProperty(value = "套餐拼音码")
    private String tcCode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "婚姻")
    private String idMarriage;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "开始年龄")
    private Integer minAge;

    @ApiModelProperty(value = "结束年龄")
    private Integer maxAge;

    @ApiModelProperty(value = "是否禁检：0.否 1.是")
    private Integer fPaused;

    @ApiModelProperty(value = "分中心ids")
    private List<String> branchIds;

    @ApiModelProperty(value = "开单医生ID")
    private String userNo;

}
