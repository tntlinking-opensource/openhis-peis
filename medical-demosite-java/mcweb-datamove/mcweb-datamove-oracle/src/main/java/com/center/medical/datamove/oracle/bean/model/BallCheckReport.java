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
 * TJ团检报告主表(BallCheckReport)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:18
 */
@Data
@TableName("BALL_CHECK_REPORT")
@ApiModel(value = "BallCheckReport", description = "TJ团检报告主表实体类")
public class BallCheckReport extends Model<BallCheckReport> implements Serializable {
    private static final long serialVersionUID = 640640731517251262L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "0:职业；1：健康")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "样本名称")
    private String sampleName;

    @ApiModelProperty(value = "套餐ID")
    private String comboId;

    @ApiModelProperty(value = "样本类型")
    private String sampleType;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "开始登记时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束登记时间")
    private Date endTime;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "0草稿1提交2通过3不通过4撤回5主检未审6主检开审7主检已审")
    private Integer status;

    @ApiModelProperty(value = "是否已打印")
    private String isPrint;

    @ApiModelProperty(value = "订单id")
    private String ddh;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体任务表ID")
    private String idOrgreservation;

    @ApiModelProperty(value = "创建人id")
    private String createId;

    @ApiModelProperty(value = "word存放地址")
    private String wordUrl;

    @ApiModelProperty(value = "pdf存放地址")
    private String pdfUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String reason;

    @ApiModelProperty(value = "参检人员构成情况图（男）")
    private String picInspectM;

    @ApiModelProperty(value = "参检人员构成情况图(女）")
    private String picInspectW;

    @ApiModelProperty(value = "参检人员构成情况图（综合）")
    private String picInspectT;

    @ApiModelProperty(value = "男女合计各年龄段构成（柱型）")
    private String picAgeColumnar;

    @ApiModelProperty(value = "男女合计各年龄段构成（饼图）")
    private String picAgePie;

    @ApiModelProperty(value = "女合计各年龄段构成（饼图）")
    private String picAgeW;

    @ApiModelProperty(value = "男合计各年龄段构成（饼图）")
    private String picAgeM;

    @ApiModelProperty(value = "横坐标为在上表中各体检项目的行号")
    private String itemTotal;

    @ApiModelProperty(value = "男性前十大异常结果(单位:人)")
    private String exceptionM;

    @ApiModelProperty(value = "女性前十大异常结果(单位:人)")
    private String exceptionW;

    @ApiModelProperty(value = "男女综合前十大异常结果(单位:人)")
    private String exceptionT;

    @ApiModelProperty(value = "${column.comment}")
    private String sglx;

    @ApiModelProperty(value = "${column.comment}")
    private Integer scsflj;

    @ApiModelProperty(value = "上次职业健康检查情况")
    private String bgfx;

    @ApiModelProperty(value = "报告生成人")
    private String generateName;

    @ApiModelProperty(value = "报告生成时间")
    private Date generateDate;

    @ApiModelProperty(value = "报告生成错误信息")
    private String generateHint;

    @ApiModelProperty(value = "是否含未检（1含0不含）")
    private Integer hasUnchecked;

    @ApiModelProperty(value = "本次职业健康检查情况")
    private String bcbgfx;

    @ApiModelProperty(value = "${column.comment}")
    private Integer scbs;

    @ApiModelProperty(value = "${column.comment}")
    private Integer createStatus;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sex;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;

    @ApiModelProperty(value = "${column.comment}")
    private String progress;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepart;
}
