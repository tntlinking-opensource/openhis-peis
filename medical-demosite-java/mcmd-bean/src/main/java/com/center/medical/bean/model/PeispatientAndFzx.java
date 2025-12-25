package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分组分中心(PeispatientAndFzx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_and_fzx")
@ApiModel(value = "PeispatientAndFzx", description = "分组分中心实体类")
public class PeispatientAndFzx extends Model<PeispatientAndFzx> implements Serializable {
    private static final long serialVersionUID = -13966193378207734L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "父级id")
    private String patientId;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0未下载 1已下载")
    private Integer xzzt;


    public PeispatientAndFzx() {
        super();
    }

    public PeispatientAndFzx(String fzxId, String patientId, Integer xzzt) {
        super();
        this.fzxId = fzxId;
        this.patientId = patientId;
        this.xzzt = xzzt;
    }
}
