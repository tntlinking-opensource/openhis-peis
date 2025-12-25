package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 领导目标表(Leadertarget)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:06
 */
@Data
@TableName("md_leadertarget")
@ApiModel(value = "Leadertarget", description = "领导目标表实体类")
public class Leadertarget extends Model<Leadertarget> implements Serializable {
    private static final long serialVersionUID = -92879336807846717L;

    @TableField(exist = false)
    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @Excel(name = "销售经理")
    @TableField(exist = false)
    @ApiModelProperty(value = "用户名称")
    private String username;

    @Excel(name = "分中心")
    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "第一季度目标")
    private Double dyjdmb;

    @ApiModelProperty(value = "第二季度目标")
    private Double dejdmb;

    @ApiModelProperty(value = "第三季度目标")
    private Double dsjdmb;

    @ApiModelProperty(value = "第四季度目标")
    private Double dijdmb;

    @Excel(name = "目标额（元）")
    @ApiModelProperty(value = "全年目标,就是target")
    private Double ndmb;

    @Excel(name = "实际完成额（元）")
    @TableField(exist = false)
    @ApiModelProperty(value = "实际完成额")
    private Double complete;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;


    @TableField(exist = false)
    @ApiModelProperty(value = "用户id")
    private String userid;


    @TableField(exist = false)
    @ApiModelProperty(value = "全年目标target")
    private String target;


    @Excel(name = "工作年龄")
    @TableField(exist = false)
    @ApiModelProperty(value = "工作年龄")
    private String workingAge;


    @TableField(exist = false)
    @ApiModelProperty(value = "用户id，实际就是userid")
    private String leaUserId;

}
