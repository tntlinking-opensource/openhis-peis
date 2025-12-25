package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TJ团检报告主表(BallCheckReport)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_ball_check_report")
@ApiModel(value = "BallCheckReport", description = "TJ团检报告主表实体类")
public class BallCheckReport extends Model<BallCheckReport> implements Serializable {
    private static final long serialVersionUID = 714688106206030281L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "类型：0.职业 1.健康")
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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "报告状态，报告状态：0.草稿 1.提交 2.通过 3.不通过 4.撤回 5.主检未审 6.主检开审 7.主检已审")
    private Integer status;

    @ApiModelProperty(value = "是否已打印：0或null.否 1.是")
    private Integer isPrint;

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

    @ApiModelProperty(value = "上岗类型(多个，逗号,现改为单选)")
    private String sglx;

    @ApiModelProperty(value = "上次是否来检")
    private Integer scsflj;

    @ApiModelProperty(value = "上次职业健康检查情况")
    private String bgfx;

    @ApiModelProperty(value = "报告生成人")
    private String generateName;

    @ApiModelProperty(value = "报告生成时间")
    private Date generateDate;

    @ApiModelProperty(value = "报告生成错误信息")
    private String generateHint;

    @ApiModelProperty(value = "是否含未检：0.不含 1.含")
    private Integer hasUnchecked;

    @ApiModelProperty(value = "本次职业健康检查情况")
    private String bcbgfx;

    @ApiModelProperty(value = "上传标志：0.未上传 1.已上传")
    private Integer scbs;

    @ApiModelProperty(value = "性别：0.男 1.女  null.通用")
    private Integer sex;

    @ApiModelProperty(value = "报告生成状态：0或Null未生成 1.生成中 2.已生成")
    private Integer createStatus;

    @ApiModelProperty(value = "configId")
    private String configId;

    @ApiModelProperty(value = "生成进度")
    private Double progress;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "ball_check_report")
    private Integer ballCheckReport;

    @ApiModelProperty(value = "未通过原因")
    private String reason;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;
}
