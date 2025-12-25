package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 普通预览科室报告 主题数据
 */
@Data
public class DiagnosticBodyDto implements Serializable {
    private static final long serialVersionUID = 8469909909281389046L;

    //c13 c14

    @ApiModelProperty(value = "收费项目打印名称")
    private String feeName;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "用于存放该检查项目下的所有体证词所拼接的字符串。")
    private String signList;

    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "审核时间")
    private String auditTime;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "检查人姓名")
    private String rummagerName;


    //职业性问诊

    @ApiModelProperty(value = "文化程度，详见：CulturalLevel")
    private Integer cultural;

    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;

    @ApiModelProperty(value = "饮酒史-是否不饮酒")
    private String noKissTheCup;

    @ApiModelProperty(value = "既往病名(多个ID逗号连接)")
    private String everOfDisease;

    @ApiModelProperty(value = "饮酒史-是否偶饮酒")
    private String betweenKissTheCup;

    @ApiModelProperty(value = "月经及生育史-初潮")
    private String ccnl;

    @ApiModelProperty(value = "饮酒史-是否经常饮酒")
    private String evermoreKiss;

    @ApiModelProperty(value = "月经及生育史-经期")
    private String jq;

    @ApiModelProperty(value = "饮酒史-是否戒酒")
    private String abstainLostKiss;

    @ApiModelProperty(value = "月经及生育史-周期")
    private String zq;

    @ApiModelProperty(value = "饮酒史-饮酒年数")
    private String kissYearN;

    @ApiModelProperty(value = "月经及生育史-停经年龄")
    private String tjnl;

    @ApiModelProperty(value = "饮酒史-饮酒量")
    private String kissAmount;

    @ApiModelProperty(value = "月经及生育史-现有子女")
    private String familyNumber;

    @ApiModelProperty(value = "饮酒史-饮酒种类")
    private String kissType;

    @ApiModelProperty(value = "月经及生育史-早产")
    private String zc;

    @ApiModelProperty(value = "家族病史")
    private String familyOfDisease;

    @ApiModelProperty(value = "月经及生育史-死产")
    private String sc;

    @ApiModelProperty(value = "症状")
    private String symptom;

    @ApiModelProperty(value = "月经及生育史-流产")
    private String lc;

    @ApiModelProperty(value = "月经及生育史-先天畸形")
    private String jt;

    @ApiModelProperty(value = "月经及生育史-异常胎")
    private String ywrc;

    @ApiModelProperty(value = "吸烟史-吸烟年数")
    private String smokeYear;

    @ApiModelProperty(value = "吸烟史-吸烟情况 0：从不吸烟 ；1：偶尔吸烟；2：以往曾经吸烟，现已戒除；3：经常吸烟")
    private String abstainSmokeNote;

    @ApiModelProperty(value = "既往病备注")
    private String everOfDiseaseRemark;

    @ApiModelProperty(value = "吸烟史-每天吸烟包数")
    private String everydaySmokeN;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "截止年月")
    private Date stopDate;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "部门")
    private String branch;

    @ApiModelProperty(value = "工种")
    private String typeOfWork;

    @ApiModelProperty(value = "毒害种类和名称")
    private String occupationHarm;

    @ApiModelProperty(value = "病名")
    private String occupationDiseast;

    @ApiModelProperty(value = "诊断日期")
    private Date diagnosisDate;

    @ApiModelProperty(value = "诊断单位")
    private String diagnosisDept;

    @ApiModelProperty(value = "状态，0或空未检，1已检")
    private String status;

    //肺功能
    @ApiModelProperty(value = "用力肺活量(测定值)")
    private Double fvc;

    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1)）")
    private Double fev;

    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)")
    private Double fevFvc;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF）")
    private Double mmef;

    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）")
    private Double feffc;

    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）")
    private Double feffb;

    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）")
    private Double feffa;

}
