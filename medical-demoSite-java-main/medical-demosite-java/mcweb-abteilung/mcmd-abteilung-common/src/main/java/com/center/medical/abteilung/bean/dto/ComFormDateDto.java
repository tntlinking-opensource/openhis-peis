package com.center.medical.abteilung.bean.dto;

import com.center.medical.bean.enums.Jktjzt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 保存更新数据
 */
@Data
public class ComFormDateDto implements Serializable {
    private static final long serialVersionUID = 7067551426139823929L;

    @ApiModelProperty(value = "XID")
    private String id;

    @ApiModelProperty(value = "是否已审核（职业性问诊）")
    private Integer isAudit;

    @ApiModelProperty(value = "职业锁定完成")
    private Double idGuidenurse;

    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    @ApiModelProperty(value = "职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 " +
            "6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取")
    private Integer zytjzt;

    @ApiModelProperty(value = "职业锁定人")
    private String parsedassignedsuiteandfi;

    @ApiModelProperty(value = "复查通知单word路径")
    private String parsedassignedgroupandfi;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "邮政编码")
    private Integer yzbm;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "地区ID")
    private String idResarea;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "既往病ids")
    private String everOfDisease;

    @ApiModelProperty(value = "既往病备注")
    private String everOfDiseaseRemark;

    @ApiModelProperty(value = "初潮年龄")
    private String ccnl;

    @ApiModelProperty(value = "经期")
    private String jq;

    @ApiModelProperty(value = "周期")
    private String zq;

    @ApiModelProperty(value = "停经年龄")
    private String tjnl;

    @ApiModelProperty(value = "现有子女人数")
    private String familyNumber;

    @ApiModelProperty(value = "流产")
    private String lc;

    @ApiModelProperty(value = "早产")
    private String zc;

    @ApiModelProperty(value = "死产")
    private String sc;

    @ApiModelProperty(value = "异位妊娠")
    private String ywrc;

    @ApiModelProperty(value = "畸胎")
    private String jt;

    @ApiModelProperty(value = "每天吸烟支数")
    private String everydaySmokeN;

    @ApiModelProperty(value = "吸烟年数")
    private String smokeYear;

    @ApiModelProperty(value = "饮酒量")
    private String kissAmount;

    @ApiModelProperty(value = "经常饮酒年数")
    private String kissYearN;

    @ApiModelProperty(value = "饮酒种类")
    private String kissType;

    @ApiModelProperty(value = "创建时间")
    private Date datecreated;

    @ApiModelProperty(value = "吸烟情况")
    private String abstainSmokeNote;

    @ApiModelProperty(value = "家族病史")
    private String familyOfDisease;

    @ApiModelProperty(value = "症状")
    private String symptom;


    @ApiModelProperty(value = "饮酒史-是否戒酒")
    private String abstainLostKiss;

    @ApiModelProperty(value = "饮酒史-是否偶饮酒")
    private String betweenKissTheCup;

    @ApiModelProperty(value = "饮酒史-是否经常饮酒")
    private String evermoreKiss;

    @ApiModelProperty(value = "是否不饮酒")
    private String noKissTheCup;

    @ApiModelProperty(value = "其他")
    private String other;


    @ApiModelProperty(value = "饮酒史-饮酒月数")
    private String kissMonth;

    @ApiModelProperty(value = "吸烟史-吸烟月数")
    private String smokeMonth;

}
