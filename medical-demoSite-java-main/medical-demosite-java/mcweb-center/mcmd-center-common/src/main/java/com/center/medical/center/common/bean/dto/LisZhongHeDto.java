package com.center.medical.center.common.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 瑞美lis数据库视图结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LisZhongHeDto {
    private String  hospBarCode; //体检号
    private String  patName; //姓名
    private String  reportDoctor; //检验师+检查人
    private Date  jyrqq;//检测日期
    private String  hospTestId;//检查项目CODE
    private String  reportResult;//结果
    private String  testState;//状态 结果提示
    private String  reference;//参考范围
    private String  itemunit;//项目单位
    private String  sampleno;//lis代码
    private String  sex;//体检者性别
    private String  verifiedDoctor;//审核人姓名
    private String  checkdatetime;//审核日期
//    private String  checkercode;//审核人代码
//    private String  techniciancode;//检查人代码
//    private String  wzms;//文字描述

}
