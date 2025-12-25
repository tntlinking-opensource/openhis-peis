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
 * KF回访记录表(VisitWrite)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_visit_write")
@ApiModel(value = "VisitWrite", description = "KF回访记录表实体类")
public class VisitWrite extends Model<VisitWrite> implements Serializable {
    private static final long serialVersionUID = -51275355452208951L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "满意度ID")
    private String satisfactionId;

    @ApiModelProperty(value = "回访结果")
    private String visitResult;

    @ApiModelProperty(value = "0:不满意回访；1：再次不满意回访")
    private Integer visitLevel;

    @ApiModelProperty(value = "回访人ID")
    private String visitterId;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String memo;
}
