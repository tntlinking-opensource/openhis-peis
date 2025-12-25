package com.center.medical.statistics.bean.vo;

import com.center.medical.bean.enums.ExamStatus;
import com.center.medical.bean.enums.Jktjzt;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检状态明细 分页返回数据
 */
@Data
public class GroupStatusVo implements Serializable {
    private static final long serialVersionUID = -2447785981114427886L;

    @Excel(name="登记日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "婚姻" ,readConverterExp = "1=未婚,2=已婚,3=离异,4=丧偶,5=其他")
    @ApiModelProperty(value = "婚姻id")
    private String idMarriage;

    @Excel(name = "体检单位")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @Excel(name = "体检套餐")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;


    @Excel(name = "团体部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "体检状态")
    @ApiModelProperty(value = "体检状态")
    private String tjzt;

    /**
     * @see Jktjzt
     */
    @Excel(name = "健康报告状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "健康总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    /**
     * @see ExamStatus
     */
    @Excel(name = "职业体检状态", readConverterExp = "null=总检未开始,-1=总检未开始,0=总检开始,1=总检完成,2=报告已打印,3=报告一审通过,4=报告一审不通过,5=二审通过," +
            "6=二审不通过,7=终审通过,8=终审不通过,9=报告已交接,10=报告已通知,11=报告已领取")
    @ApiModelProperty(value = "职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 " +
            "6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取")
    private Integer zytjzt;

    @Excel(name = "未检项目")
    @ApiModelProperty(value = "未检项目")
    private String unchecked;

    @ApiModelProperty(value = "已登记：0.未登记 1.已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "订单号")
    private String ddh;


    @Excel(name = "科室小结")
    @ApiModelProperty(value = "小结")
    private String conclusions;

    @Excel(name="注册时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
}
