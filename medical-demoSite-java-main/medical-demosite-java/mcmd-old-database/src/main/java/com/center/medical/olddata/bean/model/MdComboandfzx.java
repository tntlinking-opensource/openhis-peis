package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 最小套餐与分中心关联表(MdComboandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-25 22:05:50
 */
@Data
@TableName("md_comboandfzx")
@ApiModel(value = "MdComboandfzx", description = "最小套餐与分中心关联表实体类")
public class MdComboandfzx extends Model<MdComboandfzx> implements Serializable {
    private static final long serialVersionUID = -98195478638727045L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态：0.未同步 1.同步 2.更新")
    private Integer tbzt;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;


    public MdComboandfzx(String tcid, String fzxid) {
        this.tcid = tcid;
        this.fzxid = fzxid;
    }

    public MdComboandfzx() {
    }
}
