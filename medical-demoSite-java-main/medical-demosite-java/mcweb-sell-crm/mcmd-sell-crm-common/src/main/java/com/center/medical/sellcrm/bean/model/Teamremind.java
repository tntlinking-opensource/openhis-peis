package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户预检跟踪表(Teamremind)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 14:52:37
 */
@Data
@TableName("crm_teamremind")
@ApiModel(value = "Teamremind", description = "客户预检跟踪表实体类")
public class Teamremind extends Model<Teamremind> implements Serializable {
    private static final long serialVersionUID = 971545962043919559L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "客户单位ID")
    private String khdwid;

    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @ApiModelProperty(value = "是否已检：0或null.否 1.是")
    private Integer isExamed;


}
