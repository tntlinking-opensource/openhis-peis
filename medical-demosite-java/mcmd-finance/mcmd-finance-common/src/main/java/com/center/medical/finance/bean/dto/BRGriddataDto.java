package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 下方表格数据
 */
@Data
public class BRGriddataDto implements Serializable {
    private static final long serialVersionUID = 1196967391064790781L;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "分检是否已审核")
    private Integer isAudit;

    @ApiModelProperty(value = "修改人")
    private String idChange;

    @ApiModelProperty(value = "客户名称")
    private String customername;

    @ApiModelProperty(value = "客户id：团体号/体检号/卡号一类的")
    private String idCustomer;

    @ApiModelProperty(value = "实付金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "收费员姓名")
    private String displayIdFeecharger;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "结算方式的ID")
    private String idRemittanceway;

    @ApiModelProperty(value = "是否同步：0.未同步 1.已同步")
    private String isUpdate;

    @ApiModelProperty(value = "创建人ID")
    private String idCreator;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

}
