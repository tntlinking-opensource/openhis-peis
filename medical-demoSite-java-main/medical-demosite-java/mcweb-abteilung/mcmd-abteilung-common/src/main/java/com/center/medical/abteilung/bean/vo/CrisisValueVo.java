package com.center.medical.abteilung.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 危急值提报分页返回数据
 */
@Data
public class CrisisValueVo implements Serializable {
    private static final long serialVersionUID = -4152272772509052617L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "危机程度",readConverterExp = "1=高,2=中,3=低")
    @ApiModelProperty(value = "危急值级别")
    private String wjzjb;

    @Excel(name = "提报状态",readConverterExp = "0=已提报,1=已处理")
    @ApiModelProperty(value = "提报状态")
    private String reportstatus;

    @Excel(name = "回访处理状态",readConverterExp = "0=已提报,1=继续提报,2=已完成,3=已取消")
    @ApiModelProperty(value = "回访处理状态：0.已提报 1.继续提报 2.已完成 3.已取消")
    private Integer hfclzt;

    @Excel(name = "专家处理状态",readConverterExp = "null=未处理,0=未处理,1=已完成")
    @ApiModelProperty(value = "专家处理状态 0未处理  1 已处理")
    private Integer zjclzt;

    @Excel(name = "提报科室")
    @ApiModelProperty(value = "提报科室")
    private String reportDivision;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String tjid;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @Excel(name = "性别",readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String xb;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String nl;

    @Excel(name = "公司")
    @ApiModelProperty(value = "公司")
    private String orgName;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "电话")
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "提报时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "提报时间")
    private Date reportTime;

    @Excel(name = "体检结果")
    @ApiModelProperty(value = "危机值小结")
    private String wjzxj;

    @Excel(name = "业务处理人")
    @ApiModelProperty(value = "业务处理人用户名")
    private String ywclr;

    @Excel(name = "业务处理时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "业务处理时间")
    private Date ywclsj;

    @Excel(name = "业务处理备注")
    @ApiModelProperty(value = "业务处理备注")
    private String ywbz;

    @Excel(name = "回访处理人")
    @ApiModelProperty(value = "回访处理人用户名")
    private String hfclr;

    @Excel(name = "回访处理时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "回访处理时间")
    private Date hfclsj;

    @Excel(name = "回访处理方式", readConverterExp = "0=当面告知,1=电话通知,2=短信通知,3=继续提报")
    @ApiModelProperty(value = "回访发放方式")
    private Integer hfclfs;

    @Excel(name = "回访处理备注")
    @ApiModelProperty(value = "回访备注")
    private String hfbz;

    @Excel(name = "专家处理人")
    @ApiModelProperty(value = "专家处理人用户名")
    private String zjclr;

    @Excel(name = "专家处理时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "专家处理时间")
    private Date zjclsj;

    @Excel(name = "专家处理方式", readConverterExp = "0=当面告知,1=电话通知,2=短信通知,3=继续提报")
    @ApiModelProperty(value = "专家处理方式")
    private String zjclfs;

    @Excel(name = "专家处理备注")
    @ApiModelProperty(value = "专家处理备注")
    private String zjbz;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @Excel(name = "业务处理状态", readConverterExp = "null=未处理,''=未处理,0=未处理,1=已处理")
    @ApiModelProperty(value = "业务处理状态")
    private String ywclzt;
}
