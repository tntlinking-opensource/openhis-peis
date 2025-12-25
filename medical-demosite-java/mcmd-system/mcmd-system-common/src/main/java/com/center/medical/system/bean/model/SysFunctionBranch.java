package com.center.medical.system.bean.model;


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
 * 业务功能-分中心关联(SysFunctionBranch)表实体类
 *
 * @author makejava
 * @since 2024-03-19 11:12:09
 */
@Data
@TableName("sys_function_branch")
@ApiModel(value = "SysFunctionBranch", description = "业务功能-分中心关联实体类")
public class SysFunctionBranch extends Model<SysFunctionBranch> implements Serializable {
    private static final long serialVersionUID = 550370849928699233L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "关联ID")
    private String id;

    @ApiModelProperty(value = "业务功能ID（关联数据表sys_function的ID）")
    private Integer functionId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "状态：0.正常 1.关闭")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
