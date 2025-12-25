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
 * 检查项目和分中心关联表(MdExamAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
@Data
@TableName("md_exam_and_fzx")
@ApiModel(value = "MdExamAndFzx", description = "检查项目和分中心关联表实体类")
public class MdExamAndFzx extends Model<MdExamAndFzx> implements Serializable {
    private static final long serialVersionUID = 900877419337920646L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "同步状态：0或NULL 未同步 1.已同步 ")
    private Integer tbzt;
}
