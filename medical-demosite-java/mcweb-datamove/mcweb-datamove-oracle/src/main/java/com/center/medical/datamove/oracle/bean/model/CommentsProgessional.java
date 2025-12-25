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
 * 这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得(CommentsProgessional)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:43
 */
@Data
@TableName("COMMENTS_PROGESSIONAL")
@ApiModel(value = "CommentsProgessional", description = "这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得实体类")
public class CommentsProgessional extends Model<CommentsProgessional> implements Serializable {
    private static final long serialVersionUID = -40691292982063061L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "职业处理意见ID（基础数据中的）")
    private String progessionalId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
}
