package com.center.medical.center.common.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 虹桥lis数据库视图结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LisHqDto {
    //检查日期
    private String ndate;
    //检查时间
    private String ntime;
    //收费项目代码
    private String itemCode;
    //检查项目代码
    private String examCode;
    //LIS样本标号
    private Integer lisybbh;
    //数字结果
    private Double examItemValuesNumber;
    //字符结果
    private String examItemValuesShort;
    //参考范围（lis范围）
    private String refRange;
    //
    private String status;
    //单位
    private String units;
    //Lis代码
    private String lisCode;
    //检查医师
    private String examDoctor;
    //审核人
    private String auditName;
    //检验师
    private String inspectName;
    //审核日期
    private String auditDate;
    //审核时间
    private String auditTime;
    //检查医生代码
    private String inspectCode;
    //收样日期
    private String receiveDate;
    //收样时间
    private String receiveTime;


}
