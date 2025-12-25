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
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(MdFxReviewInfo)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Data
@TableName("md_fx_review_info")
@ApiModel(value = "MdFxReviewInfo", description = "职业健康检查职业病危害效应相关指标异常需要复查人员实体类")
public class MdFxReviewInfo extends Model<MdFxReviewInfo> implements Serializable {
    private static final long serialVersionUID = 936022641748390825L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素ID")
    private String harmId;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "职业禁忌症名称")
    private String diagnosis;

    @ApiModelProperty(value = "人数")
    private Integer num;

    @ApiModelProperty(value = "建议")
    private String summaryText;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;
}
