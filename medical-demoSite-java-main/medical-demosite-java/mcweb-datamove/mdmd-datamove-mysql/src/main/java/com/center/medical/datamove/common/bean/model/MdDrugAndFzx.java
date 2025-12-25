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
 * 药品分中心映射(MdDrugAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
@Data
@TableName("md_drug_and_fzx")
@ApiModel(value = "MdDrugAndFzx", description = "药品分中心映射实体类")
public class MdDrugAndFzx extends Model<MdDrugAndFzx> implements Serializable {
    private static final long serialVersionUID = 183930006636563617L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "药物id")
    private String drugId;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "状态")
    private Integer tbzt;
}
