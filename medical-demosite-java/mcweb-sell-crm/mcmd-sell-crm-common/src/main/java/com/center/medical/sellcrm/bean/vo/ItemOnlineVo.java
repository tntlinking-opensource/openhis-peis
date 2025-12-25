package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 线上备单返回数据
 */
@Data
public class ItemOnlineVo implements Serializable {
    private static final long serialVersionUID = 6494568889242218196L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "订单名称")
    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @Excel(name = "客户团体名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @Excel(name = "任务预定日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @Excel(name = "计划结束日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "男性体检人数")
    private Integer nxtjrs;

    @ApiModelProperty(value = "女性体检人数")
    private Integer vxtjrs;

    @Excel(name = "预计人数")
    @ApiModelProperty(value = "预计人数(男性体检人数+女性体检人数)")
    private Integer yjrs;

    @Excel(name = "已备单人数")
    @ApiModelProperty(value = "已备单人数")
    private String nums;

    @Excel(name = "团体联系方式")
    @ApiModelProperty(value = "客户单位电话")
    private String khdwdh;

    @Excel(name = "团体地址")
    @ApiModelProperty(value = "客户单位注册地址")
    private String khdwzcdz;

    @Excel(name = "前台须知")
    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "文件路径(名单)")
    private String urls;

    @ApiModelProperty(value = "状态")
    private Integer tbzt;

    @JsonProperty("fFinished")
    @ApiModelProperty(value = "任务已完成")
    private Integer fFinished;

    @ApiModelProperty(value = "变更状态，订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更")
    private Integer bgzt;

    @ApiModelProperty(value = "提交审核日期")
    private Date submitTime;

    @ApiModelProperty(value = "完成日期")
    private Date bdrq;

    @ApiModelProperty(value = "变更备注")
    private String bgmemo;

    @ApiModelProperty(value = "是否线上备单：null或其他.未线上备单 1.已线上备单")
    private Integer isOnline;

    @ApiModelProperty(value = "开单助理用户名")
    private String kdzlName;
}
