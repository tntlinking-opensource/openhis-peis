package com.center.medical.report.bean.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TJ团检报告主表(BallCheckReport)表实体类
 *
 * @author fjj
 * @since 2022-11-08 17:51:54
 */
@Data
public class BallCheckReportVo extends Model<BallCheckReportVo> implements Serializable {
    private static final long serialVersionUID = 714688106206030282L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String ddid;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @ApiModelProperty(value = "类型：0.健康 1.职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "样本名称")
    private String sampleName;

    @ApiModelProperty(value = "样本类型")
    private String sampleType;

    @ApiModelProperty(value = "订单号")
    private String orderId;


    @ApiModelProperty(value = "开始登记时间")
    private Date beginTime;


    @ApiModelProperty(value = "结束登记时间")
    private Date endTime;

    @ApiModelProperty(value = "上岗类型(多个，逗号,现改为单选)")
    private String sglx;

    @ApiModelProperty(value = "上次是否来检")
    private Integer scsflj;

    @ApiModelProperty(value = "上次职业健康检查情况")
    private String bgfx;

    @ApiModelProperty(value = "本次职业健康检查情况")
    private String bcbgfx;

    @ApiModelProperty(value = "性别：0.男 1.女  null.通用")
    private Integer sex;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "是否含未检：0.不含 1.含")
    private Integer hasUnchecked;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

}
