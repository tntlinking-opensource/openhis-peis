package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户公共池领取与领取人员关联表(Receiveandsell)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:10
 */
@Data
@TableName("crm_receiveandsell")
@ApiModel(value = "Receiveandsell", description = "客户公共池领取与领取人员关联表实体类")
public class Receiveandsell extends Model<Receiveandsell> implements Serializable {
    private static final long serialVersionUID = 561324587661378589L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户公共池ID")
    private String clientid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "操作日期")
    private Date lqrq;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "状态：0.领取 1.分配")
    private Integer czzt;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @TableField(exist = false)
    @ApiModelProperty(value = "销售经理名称")
    private String xs;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String fzx;
}
