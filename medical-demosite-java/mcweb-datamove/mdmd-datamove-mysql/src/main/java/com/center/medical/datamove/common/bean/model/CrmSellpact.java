package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 销售合同维护表(CrmSellpact)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
@Data
@TableName("crm_sellpact")
@ApiModel(value = "CrmSellpact", description = "销售合同维护表实体类")
public class CrmSellpact extends Model<CrmSellpact> implements Serializable {
    private static final long serialVersionUID = -84036385909870580L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "合同名称")
    private String htmc;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "合同签订日期")
    private Date htqdrq;

    @ApiModelProperty(value = "体检开始日期")
    private Date tjksrq;

    @ApiModelProperty(value = "体检结束日期")
    private Date tjjsrq;

    @ApiModelProperty(value = "客户单位名称id")
    private String khdwmcid;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "体检人数")
    private Integer tjrs;

    @ApiModelProperty(value = "预算")
    private Double ys;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
