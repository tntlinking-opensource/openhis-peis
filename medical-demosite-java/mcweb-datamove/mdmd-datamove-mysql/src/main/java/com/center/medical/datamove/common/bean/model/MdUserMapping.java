package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 体检用户与其他系统用户映射表(MdUserMapping)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:21
 */
@Data
@TableName("md_user_mapping")
@ApiModel(value = "MdUserMapping", description = "体检用户与其他系统用户映射表实体类")
public class MdUserMapping extends Model<MdUserMapping> implements Serializable {
    private static final long serialVersionUID = 936874763409401542L;

    @TableId(value = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "其他系统用户ID")
    private String bizUserId;

    @ApiModelProperty(value = "映射的系统id")
    private Integer systemId;

    @ApiModelProperty(value = "状态：-1删除 1.正常关联 2.冻结")
    private Integer status;

    @ApiModelProperty(value = "建立时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
