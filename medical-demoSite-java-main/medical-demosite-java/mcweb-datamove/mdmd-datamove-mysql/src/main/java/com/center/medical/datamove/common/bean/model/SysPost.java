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
 * 岗位信息表(SysPost)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:37
 */
@Data
@TableName("sys_post")
@ApiModel(value = "SysPost", description = "岗位信息表实体类")
public class SysPost extends Model<SysPost> implements Serializable {
    private static final long serialVersionUID = -67994732525751021L;

    @TableId(value = "post_id")
    @ApiModelProperty(value = "岗位ID")
    private Long postId;

    @ApiModelProperty(value = "所有上级岗位，格式：[0][1][10]")
    private String parentIds;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "显示顺序")
    private Integer postSort;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
