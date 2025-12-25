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
 * (AppRecommend)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:53
 */
@Data
@TableName("APP_RECOMMEND")
@ApiModel(value = "AppRecommend", description = "$tableInfo.comment实体类")
public class AppRecommend extends Model<AppRecommend> implements Serializable {
    private static final long serialVersionUID = -58015782373269997L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String loc;

    @ApiModelProperty(value = "${column.comment}")
    private Integer recType;

    @ApiModelProperty(value = "${column.comment}")
    private String recUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String recSort;

    @ApiModelProperty(value = "${column.comment}")
    private Integer recState;

    @ApiModelProperty(value = "${column.comment}")
    private String title;

    @ApiModelProperty(value = "${column.comment}")
    private String dataUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;
}
