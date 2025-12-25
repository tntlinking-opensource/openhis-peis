package com.center.medical.bean.model;

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
 * 每日排检上限(SortexamLimit)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_sortexam_limit")
@ApiModel(value = "SortexamLimit", description = "每日排检上限实体类")
public class SortexamLimit extends Model<SortexamLimit> implements Serializable {
    private static final long serialVersionUID = -20584674683362224L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "排检日期")
    private Date sortDate;

    @ApiModelProperty(value = "VIP人数上限")
    private Integer vip;

    @ApiModelProperty(value = "VVIP人数上限")
    private Integer vvip;

    @ApiModelProperty(value = "普通上午人数上限")
    private Integer am;

    @ApiModelProperty(value = "普通下午人数上限")
    private Integer pm;
}
