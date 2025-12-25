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
 * 客户公共池领取与领取人员关联表(CrmReceiveandsell)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
@Data
@TableName("crm_receiveandsell")
@ApiModel(value = "CrmReceiveandsell", description = "客户公共池领取与领取人员关联表实体类")
public class CrmReceiveandsell extends Model<CrmReceiveandsell> implements Serializable {
    private static final long serialVersionUID = 374413006522726036L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户公共池ID")
    private String clientid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "操作日期")
    private Date lqrq;

    @ApiModelProperty(value = "假删状态")
    private Integer isDelete;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer czzt;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
