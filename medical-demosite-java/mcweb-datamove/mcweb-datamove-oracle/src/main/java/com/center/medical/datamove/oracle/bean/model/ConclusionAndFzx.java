package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:46
 */
@Data
@TableName("CONCLUSION_AND_FZX")
@ApiModel(value = "ConclusionAndFzx", description = "JC结伦词和分中心关联表实体类")
public class ConclusionAndFzx extends Model<ConclusionAndFzx> implements Serializable {
    private static final long serialVersionUID = -24047338223090880L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "结伦词ID")
    private String conclusionId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态(0 未同步，1 已同步 )")
    private Integer tbzt;
}
