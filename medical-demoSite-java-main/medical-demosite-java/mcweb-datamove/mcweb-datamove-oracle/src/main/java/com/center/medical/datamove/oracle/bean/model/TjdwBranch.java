package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * XS体检单位：部门信息(TjdwBranch)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:31
 */
@Data
@TableName("TJDW_BRANCH")
@ApiModel(value = "TjdwBranch", description = "XS体检单位：部门信息实体类")
public class TjdwBranch extends Model<TjdwBranch> implements Serializable {
    private static final long serialVersionUID = -57733911754475035L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private String serialCode;

    @ApiModelProperty(value = "部门编码")
    private String branchCode;

    @ApiModelProperty(value = "部门名称")
    private String branchName;

    @ApiModelProperty(value = "公司编码")
    private String corpCode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}
