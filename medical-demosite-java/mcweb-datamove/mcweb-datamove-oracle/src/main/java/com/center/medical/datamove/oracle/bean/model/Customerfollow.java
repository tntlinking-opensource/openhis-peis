package com.center.medical.datamove.oracle.bean.model;


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
 * 客户跟踪表(Customerfollow)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:06
 */
@Data
@TableName("CUSTOMERFOLLOW")
@ApiModel(value = "Customerfollow", description = "客户跟踪表实体类")
public class Customerfollow extends Model<Customerfollow> implements Serializable {
    private static final long serialVersionUID = 853507092579734520L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "主题")
    private String zt;

    @ApiModelProperty(value = "客户负责人")
    private String khfzr;

    @ApiModelProperty(value = "便于维护是哪个销售经理填写的记录")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "跟进阶段")
    private String gjjd;

    @ApiModelProperty(value = "跟进日期")
    private Date gjrq;

    @ApiModelProperty(value = "跟进内容")
    private String gjnr;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date jsrq;
}
