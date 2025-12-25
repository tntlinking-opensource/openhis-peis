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
 * KF迟捡、阳性、不合格样本回访(FailTotalVisit)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:40
 */
@Data
@TableName("FAIL_TOTAL_VISIT")
@ApiModel(value = "FailTotalVisit", description = "KF迟捡、阳性、不合格样本回访实体类")
public class FailTotalVisit extends Model<FailTotalVisit> implements Serializable {
    private static final long serialVersionUID = -68259678421104915L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "回访类型")
    private Integer visitType;

    @ApiModelProperty(value = "体检号")
    private String personcode;

    @ApiModelProperty(value = "回访人ID")
    private String visitId;

    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    private Date modifydate;

    @ApiModelProperty(value = "这里存放的是VISIT_MAIN（迟检、阳性、不合格样本人员表）的ID")
    private String visitMainId;

    @ApiModelProperty(value = "记录客户是否来检，若不来检，直接弃检，若来检，记录预约时间")
    private Double sflj;

    @ApiModelProperty(value = "规定来检时间，还不过来延长迟捡时间，延长时间还没来，再回访一次")
    private Date ljsj;

    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @ApiModelProperty(value = "跟进结果")
    private Integer visitResult;

    @ApiModelProperty(value = "${column.comment}")
    private Integer noticeAgain;

    @ApiModelProperty(value = "${column.comment}")
    private Date preTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer preResult;
}
