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
 * ZJ复查表(Review)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:21
 */
@Data
@TableName("REVIEW")
@ApiModel(value = "Review", description = "ZJ复查表实体类")
public class Review extends Model<Review> implements Serializable {
    private static final long serialVersionUID = 487485789872600733L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "复查时间起")
    private Date dateFrom;

    @ApiModelProperty(value = "复查时间止")
    private Date dateTo;

    @ApiModelProperty(value = "复查状态")
    private Integer callbackStation;

    @ApiModelProperty(value = "操作员ID")
    private String userId;

    @ApiModelProperty(value = "注意事项")
    private String noticeOfProceedingText;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "复查PDF地址")
    private String reviewPdf;
}
