package com.center.medical.datamove.admin.bean.model;


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
 * 普通套餐与分中心关联表(Mealandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-25 22:25:30
 */
@Data
@TableName("MEALANDFZX")
@ApiModel(value = "Mealandfzx", description = "普通套餐与分中心关联表实体类")
public class Mealandfzx extends Model<Mealandfzx> implements Serializable {
    private static final long serialVersionUID = 920831236635124016L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态(0:未同步、1:同步、2:更新)")
    private Integer tbzt;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
