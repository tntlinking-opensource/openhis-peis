package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CustomerTransfer)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:50
 */
@Data
@TableName("crm_customer_transfer")
@ApiModel(value = "CustomerTransfer", description = "客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。实体类")
public class CustomerTransfer extends Model<CustomerTransfer> implements Serializable {
    private static final long serialVersionUID = 217889823238249131L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "转移时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "原销售经理id")
    private String fromXsjlid;

    @ApiModelProperty(value = "变更至销售经理id")
    private String toXsjlid;

    @ApiModelProperty(value = "变更至销售经理名称")
    private String toXsjl;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0.未下载 1.已下载")
    private Integer xzzt;

    @ApiModelProperty(value = "下载时间")
    private Date xzdate;

    @ApiModelProperty(value = "客户id")
    private String customerId;


    public CustomerTransfer(String fromXsjlid, String toXsjlid, String toXsjl, String fzxId, Integer xzzt,
                            Date xzdate, String customerId) {
        super();
        this.fromXsjlid = fromXsjlid;
        this.toXsjlid = toXsjlid;
        this.toXsjl = toXsjl;
        this.fzxId = fzxId;
        this.xzzt = xzzt;
        this.xzdate = xzdate;
        this.customerId = customerId;
    }

    public CustomerTransfer() {
    }
}
