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
 * 危害因素和分中心(MdHarmAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Data
@TableName("md_harm_and_fzx")
@ApiModel(value = "MdHarmAndFzx", description = "危害因素和分中心实体类")
public class MdHarmAndFzx extends Model<MdHarmAndFzx> implements Serializable {
    private static final long serialVersionUID = -80161239591081781L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目ID")
    private String harmId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步")
    private Integer tbzt;
}
