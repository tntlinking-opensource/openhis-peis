package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查看左中体检人 返回数据
 */
@Data
public class AccountsInfoVo implements Serializable {
    private static final long serialVersionUID = -5373544496342910952L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;
    
    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "体检状态")
    @ApiModelProperty(value = "检查状态")
    private String line;

    @Excel(name = "禁检", readConverterExp = "0=否,1=是" , defaultValue = "否")
    @ApiModelProperty(value = "禁检")
    private Integer fPaused;

    @Excel(name = "分组名称")
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "是否登记", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @Excel(name = "婚姻", readConverterExp = "1=未婚,2=已婚,3=离异,4=丧偶,5=其他")
    @ApiModelProperty(value = "婚姻ID")
    private Integer idMarriage;

    @Excel(name = "年龄", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "年龄")
    private String age;


    //10
    @Excel(name = "是否替检", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否替检：0.否 1.是")
    private Integer countreportxml;

    @Excel(name = "原体检者")
    @ApiModelProperty(value = "替检人")
    private String tjr;

    @Excel(name = "体检类型", readConverterExp = "0=健康体检,1=职业体检,2=综合,3=复查")
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @Excel(name = "健康报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    @Excel(name = "职业报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 " +
            "6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取")
    private Integer zytjzt;


    @Excel(name = "套餐")
    @ApiModelProperty(value = "体检套餐名称")
    private String examName;

    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原始价格")
    private Double price;

    @Excel(name = "套餐原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "套餐原始价格")
    private Double tcyj;

    @Excel(name = "套餐优惠价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "折后价格")
    private Double tcyhj;

    @Excel(name = "统收实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "统收实收")
    private Double ssts;

    @Excel(name = "康淘实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "康淘实收")
    private Double sskt;

    @Excel(name = "其他实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "其他实收")
    private Double ssqt;

    @Excel(name = "合计实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "合计实收")
    private Double sshj;

    @Excel(name = "加项实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "加项实收")
    private Double ssAdd;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name="体检时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @Excel(name = "登记员")
    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "支付方式名称")
    private String chargePayway;

    @Excel(name = "输入码")
    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @Excel(name = "档案号")
    @ApiModelProperty(value = "档案号")
    private String chiveNo;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @Excel(name = "记账", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "记账")
    private Double jz;

    @Excel(name = "记账人")
    @ApiModelProperty(value = "记账人")
    private String jzr;

    @ApiModelProperty(value = "体检者id")
    private String id;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "体检号生成人")
    @ApiModelProperty(value = "备单体检号生成人")
    private String guidancenote2;

    @Excel(name = "电话")
    @ApiModelProperty(value = "手机")
    private String phone;

    @Excel(name = "结账状态", readConverterExp = "null=未结账,0=未结账,1=已结账")
    @ApiModelProperty(value = "结账状态 0或者Null：未结账 1：已结账")
    private Integer checkoutStatus;

    @Excel(name = "结账日期", dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "点击已结账的结账日期")
    private Date checkoutDate;

    @ApiModelProperty(value = "检查状态id")
    private Integer linenum;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
