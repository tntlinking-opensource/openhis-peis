package com.center.medical.reception.bean.vo;

import com.center.medical.bean.enums.OrderChangeStatus;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-12-03 13:58
 * @description: 前台备单订单数据
 */
@Data
public class BdOrderVo implements Serializable {
    private static final long serialVersionUID = -2902084065058807790L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "订单名称")
    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @Excel(name = "客户团体名称")
    @ApiModelProperty(value = "团体单位名称")
    private String khdwmc;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjlmc;


    @ApiModelProperty(value = "销售经理Id")
    private String xsjlid;

    @Excel(name="任务预定日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "任务预定日期")
    private Date jhjqc;

    @Excel(name="计划结束日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "计划结束日期")
    private Date jhjqd;

    @Excel(name = "预计人数")
    @ApiModelProperty(value = "预计人数")
    private Integer yjrs;

    @Excel(name = "已备单人数")
    @ApiModelProperty(value = "已备单人数")
    private Integer aCount; //numorgresv

    @ApiModelProperty(value = "男性体检人数")
    private Integer nxtjrs;

    @ApiModelProperty(value = "女性体检人数")
    private Integer vxtjrs;

    @ApiModelProperty(value = "预计人数")
    private Integer plancount;

    @Excel(name = "团体联系方式")
    @ApiModelProperty(value = "客户单位电话")
    private String khdwdh;

    @Excel(name = "团体地址")
    @ApiModelProperty(value = "团体地址")
    private String khdwzcdz;

    @Excel(name = "前台须知")
    @ApiModelProperty(value = "前台须知")
    private String qtxz;



    @ApiModelProperty(value = "名单数量")
    private String urls;

    @ApiModelProperty(value = "状态：0.未同步 1.同步 2.更新")
    private Integer tbzt;

    @ApiModelProperty(value = "结束状态")
    private Integer finish;

    /**
     * @see OrderChangeStatus
     */
    @ApiModelProperty(value = "变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更")
    private Integer bgzt;

    @ApiModelProperty(value = "提交审核日期")
    private Date submitTime;

    @ApiModelProperty(value = "完成日期")
    private Date bdrq;

    @ApiModelProperty(value = "变更备注")
    private String bgmemo;

    @ApiModelProperty(value = "是否线上备单：null或其他.未线上备单 1.已线上备单")
    private Integer isOnline;

    @ApiModelProperty(value = "接单经理")
    private String kdzlName;

//    @Excel(name = "体检者信息")
//    @ApiModelProperty(value = "备单导出体检者信息")
//    private List<ExportPatientDto> exportPatient;


    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

}
