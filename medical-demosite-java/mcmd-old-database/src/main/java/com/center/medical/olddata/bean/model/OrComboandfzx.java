package com.center.medical.olddata.bean.model;


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
 * 最小套餐与分中心关联表(Comboandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-25 22:07:20
 */
@Data
@TableName("COMBOANDFZX")
@ApiModel(value = "Comboandfzx", description = "最小套餐与分中心关联表实体类")
public class OrComboandfzx extends Model<OrComboandfzx> implements Serializable {
    private static final long serialVersionUID = 567241939359531337L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态(0:未同步、1:同步、2:更新)")
    private Integer tbzt;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}
