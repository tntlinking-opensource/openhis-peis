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
 * 普通套餐与分中心关联表(MdMealandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-25 22:26:21
 */
@Data
@TableName("md_mealandfzx")
@ApiModel(value = "MdMealandfzx", description = "普通套餐与分中心关联表实体类")
public class MdMealandfzx extends Model<MdMealandfzx> implements Serializable {
    private static final long serialVersionUID = 955157612860846378L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步 2.更新")
    private Integer tbzt;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
