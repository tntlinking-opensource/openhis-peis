package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者导引单CT排序(PeisDydCtSeq)表实体类
 *
 * @author ay
 * @since 2023-09-01 18:47:31
 */
@Data
@TableName("md_peis_dyd_ct_seq")
@ApiModel(value = "PeisDydCtSeq", description = "体检者导引单CT排序实体类")
public class PeisDydCtSeq extends Model<PeisDydCtSeq> implements Serializable {
    private static final long serialVersionUID = 408741399230834555L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "排序")
    private Integer seq;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    public PeisDydCtSeq(String patientcode, Integer seq) {
        this.patientcode = patientcode;
        this.seq = seq;
    }

    public PeisDydCtSeq() {
    }
}
