package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 总检-医生 关联表(TotalDoctor)表实体类
 *
 * @author 路飞船长
 * @since 2022-12-08 17:51:24
 */
@Data
@TableName("md_total_doctor")
@ApiModel(value = "TotalDoctor", description = "总检-医生 关联表实体类")
public class TotalDoctor extends Model<TotalDoctor> implements Serializable {
    private static final long serialVersionUID = 741252540498323224L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "总检ID")
    private String totalId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "类型：0当前登录医生 1额外选择的医生")
    private Integer type;
}
