package com.center.medical.datamove.oracle.bean.model;


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
 * JC检查项目和分中心关联表(ExamAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:37
 */
@Data
@TableName("EXAM_AND_FZX")
@ApiModel(value = "ExamAndFzx", description = "JC检查项目和分中心关联表实体类")
public class ExamAndFzx extends Model<ExamAndFzx> implements Serializable {
    private static final long serialVersionUID = 453988651024999688L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "检查项目ID")
    private String examId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态(0或NULL 未同步，1 已同步 )")
    private Integer tbzt;
}
