package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售合同维护表(Sellpact)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:25
 */
@Data
@TableName("crm_sellpact")
@ApiModel(value = "Sellpact", description = "销售合同维护表实体类")
public class Sellpact extends Model<Sellpact> implements Serializable {
    private static final long serialVersionUID = -73563623618904861L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "合同名称")
    private String htmc;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "合同签订日期")
    private Date htqdrq;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "体检开始日期")
    private Date tjksrq;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否置为已来检：0.否 1.是")
    private Integer isExamed;

    @TableField(exist = false)
    @ApiModelProperty(value = "文件个数")
    private Integer wjgs;


    @TableField(exist = false)
    @ApiModelProperty(value = "文件地址")
    private String filePath;
}
