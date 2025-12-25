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
 * 客户预检跟踪表(Teamremind)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:14
 */
@Data
@TableName("TEAMREMIND")
@ApiModel(value = "Teamremind", description = "客户预检跟踪表实体类")
public class Teamremind extends Model<Teamremind> implements Serializable {
    private static final long serialVersionUID = 399831246501800872L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "销售合同维护id")
    private String xshtwhid;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户联系电话")
    private String khlxdh;

    @ApiModelProperty(value = "处理人")
    private String clr;

    @ApiModelProperty(value = "处理时间")
    private Date clsj;

    @ApiModelProperty(value = "上次体检开始日期")
    private Date sctjksrq;

    @ApiModelProperty(value = "下次沟通时间")
    private Date xcgtrq;

    @ApiModelProperty(value = "处理状态(0：未处理  1：已处理)")
    private Integer clzt;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String khdwid;

    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isExamed;
}
