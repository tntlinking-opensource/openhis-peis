package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分组分中心(MdPeispatientAndFzx)表实体类
 *
 * @author ay
 * @since 2024-04-17 10:34:47
 */
@Data
@TableName("md_peispatient_and_fzx")
@ApiModel(value = "MdPeispatientAndFzx", description = "分组分中心实体类")
public class MdPeispatientAndFzx extends Model<MdPeispatientAndFzx> implements Serializable {
    private static final long serialVersionUID = -15280794756813051L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "体检者ID(md_peispatient的id)")
    private String patientId;


    @ApiModelProperty(value = "分中心id")
    private String fzxId;


    @ApiModelProperty(value = "下载状态：0未下载 1已下载")
    private Integer xzzt;


    public MdPeispatientAndFzx(String patientId, String fzxId) {
        this.patientId = patientId;
        this.fzxId = fzxId;
    }

    public MdPeispatientAndFzx() {
    }
}
