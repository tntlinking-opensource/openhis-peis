package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: 浮俊杰
 * @date: 2022-12-07 21:28
 * @description: 健康报告分页查询数据
 */
@Data
public class PeispatientVo {

    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "报告生成次数,0或null 未生成 ,其他已生成")
    private Integer createReportNum;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "健康报告打印次数")
    private Integer healthPrintNum;

    @ApiModelProperty(value = "职业报告打印次数")
    private Integer diseasePrintNum;

    @ApiModelProperty(value = "报告生成人")
    private String generateName;

    @ApiModelProperty(value = "创建日期")
    private Date generateDate;

    @ApiModelProperty(value = "报告错误提示")
    private String generateHint;

    @ApiModelProperty(value = "报告已打印")
    private String fReportprinted;

    @ApiModelProperty(value = "是否总检")
    private String isTotal;

    @ApiModelProperty(value = "开单医生ID2总检锁定人")
    private String kdys;

    @ApiModelProperty(value = "健康总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer bgzt;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;


    @ApiModelProperty(value = "通知方式")
    private String informway;

    @ApiModelProperty(value = "报告生成状态：0或Null未生成 1.生成中 2.已生成")
    private String orgDepartsubb;

    @ApiModelProperty(value = "隐私")
    private String containsPrivate;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "职业体检状态，详见：com.center.medical.bean.enums.zytjzt")
    private Integer zytjzt;

    @ApiModelProperty(value = "打印次数")
    private Integer printNum;

    @ApiModelProperty(value = "临时报告是否已生成 0未生成1已生成")
    private Integer lsbg;

    @ApiModelProperty(value = "体检者的id")
    private String pId;

    @ApiModelProperty(value = "正式报告pdf地址")
    private String urlPdf;

    @ApiModelProperty(value = "临时报告pdf地址")
    private String lsPdf;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "是否已赋码 0否1是")
    private int reportCoding;

    @ApiModelProperty(value = "隐私报告pdf地址")
    private String ysPdf;

}
