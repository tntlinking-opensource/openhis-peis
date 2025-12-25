package com.center.medical.datamove.common.bean.model;


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
 * 科室IP(MdKsIp)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Data
@TableName("md_ks_ip")
@ApiModel(value = "MdKsIp", description = "科室IP实体类")
public class MdKsIp extends Model<MdKsIp> implements Serializable {
    private static final long serialVersionUID = 550519703018442850L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室名称")
    private String name;

    @ApiModelProperty(value = "IP地址：IP+端口号")
    private String ip;

    @ApiModelProperty(value = "关联的科室ID")
    private String ksId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "状态：0.关闭 1.启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
