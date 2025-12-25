package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 药品分中心映射(DrugAndFzx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_drug_and_fzx")
@ApiModel(value = "DrugAndFzx", description = "药品分中心映射实体类")
public class DrugAndFzx extends Model<DrugAndFzx> implements Serializable {
    private static final long serialVersionUID = 676399984722655729L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "药物id")
    private String drugId;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "状态")
    private Integer tbzt;


    public DrugAndFzx() {

    }

    public DrugAndFzx(String drugId, String fzxId, Integer tbzt) {
        super();
        this.drugId = drugId;
        this.fzxId = fzxId;
        this.tbzt = tbzt;
    }
}
