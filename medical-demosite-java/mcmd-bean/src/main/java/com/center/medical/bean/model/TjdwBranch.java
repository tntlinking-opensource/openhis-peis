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
 * XS体检单位：部门信息(TjdwBranch)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_tjdw_branch")
@ApiModel(value = "TjdwBranch", description = "XS体检单位：部门信息实体类")
public class TjdwBranch extends Model<TjdwBranch> implements Serializable {
    private static final long serialVersionUID = 575410842015749206L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private Integer serialCode;

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
