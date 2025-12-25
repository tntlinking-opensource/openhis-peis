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
 * JC随访目的维护(FollowUp)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_follow_up")
@ApiModel(value = "FollowUp", description = "JC随访目的维护实体类")
public class FollowUp extends Model<FollowUp> implements Serializable {
    private static final long serialVersionUID = 459226386448246068L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "随访代码")
    private Integer followUpCode;

    @ApiModelProperty(value = "随访目的名称")
    private String followUpName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
