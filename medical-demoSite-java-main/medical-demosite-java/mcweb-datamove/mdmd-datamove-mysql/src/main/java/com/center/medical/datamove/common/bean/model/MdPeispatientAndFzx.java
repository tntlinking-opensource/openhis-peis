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
 * 分组分中心(MdPeispatientAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
@Data
@TableName("md_peispatient_and_fzx")
@ApiModel(value = "MdPeispatientAndFzx", description = "分组分中心实体类")
public class MdPeispatientAndFzx extends Model<MdPeispatientAndFzx> implements Serializable {
    private static final long serialVersionUID = -24111124688086474L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "父级id")
    private String patientId;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0未下载 1已下载")
    private Integer xzzt;
}
