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
 * 总检-医生 关联表(MdTotalDoctor)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:10
 */
@Data
@TableName("md_total_doctor")
@ApiModel(value = "MdTotalDoctor", description = "总检-医生 关联表实体类")
public class MdTotalDoctor extends Model<MdTotalDoctor> implements Serializable {
    private static final long serialVersionUID = -62094770316210215L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "总检ID")
    private String totalId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "类型：0当前登录医生 1额外选择的医生")
    private Integer type;
}
