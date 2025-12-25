package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售提醒(CrmSellRemind)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
@Data
@TableName("crm_sell_remind")
@ApiModel(value = "CrmSellRemind", description = "销售提醒实体类")
public class CrmSellRemind extends Model<CrmSellRemind> implements Serializable {
    private static final long serialVersionUID = -22887422862659967L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "客户id")
    private String khdwid;

    @ApiModelProperty(value = "客户名称")
    private String khdwmc;

    @ApiModelProperty(value = "提醒时间")
    private Date remindTime;

    @ApiModelProperty(value = "提醒内容")
    private String content;

    @ApiModelProperty(value = "创建人用户名")
    private String createName;

    @ApiModelProperty(value = "状态 0未结束 1 已结束")
    private Integer status;
}
