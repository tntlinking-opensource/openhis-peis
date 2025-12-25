package com.center.medical.query.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CDC职业病直报数据查询 导出数据
 */
@Data
public class CdcExportDto implements Serializable {
    private static final long serialVersionUID = -8941077250568252671L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检时间")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "用人单位")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "是否复查")
    @ApiModelProperty(value = "是否复查")
    private String isfc;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系方式")
    private String phone;

    @Excel(name = "体检类型")
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "总工龄年")
    @ApiModelProperty(value = "总工龄年")
    private String zglyear;

    @Excel(name = "总工龄年")
    @ApiModelProperty(value = "总工龄月")
    private String zglmonth;

    @Excel(name = "接害因素")
    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @Excel(name = "接害工龄年")
    @ApiModelProperty(value = "接害工龄年")
    private String jhglyear;

    @Excel(name = "接害工龄月")
    @ApiModelProperty(value = "接害工龄月")
    private String jhglmonth;

    @Excel(name = "车间")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "工种")
    @ApiModelProperty(value = "工种")
    private String trades;

    @Excel(name = "体检结论")
    @ApiModelProperty(value = "结论")
    private String occupationSummary;


    @Excel(name = "职业问诊")
    @ApiModelProperty(value = "症状")
    private String symptom;


    @Excel(name = "收缩压")
    @ApiModelProperty(value = "收缩压")
    private String ssy;

    @Excel(name = "舒张压")
    @ApiModelProperty(value = "舒张压")
    private String szy;

    @Excel(name = "肝脾彩超")
    @ApiModelProperty(value = "肝脾彩超")
    private String gpcc;

    @Excel(name = "肺功能FVC")
    @ApiModelProperty(value = "肺功能FVC")
    private String fvc;

    @Excel(name = "肺功能FEV1")
    @ApiModelProperty(value = "肺功能FEV1")
    private String fev1;

    @Excel(name = "肺功能FEV1/FVC")
    @ApiModelProperty(value = "肺功能FEV1/FVC")
    private String fev1Fvc;

    //电测听
    @Excel(name = "左耳气导500Hz")
    @ApiModelProperty(value = "气导左耳500Hz")
    private Double airLeft500;

    @Excel(name = "左耳气导1000Hz")
    @ApiModelProperty(value = "气导左耳1000Hz")
    private Double airLeft1000;

    @Excel(name = "左耳气导2000Hz")
    @ApiModelProperty(value = "气导左耳2000Hz")
    private Double airLeft2000;

    @Excel(name = "左耳气导3000Hz")
    @ApiModelProperty(value = "气导左耳3000Hz")
    private Double airLeft3000;

    @Excel(name = "左耳气导4000Hz")
    @ApiModelProperty(value = "气导左耳4000Hz")
    private Double airLeft4000;

    @Excel(name = "左耳气导6000Hz")
    @ApiModelProperty(value = "气导左耳6000Hz")
    private Double airLeft6000;

    @Excel(name = "右耳气导500Hz")
    @ApiModelProperty(value = "气导右耳500Hz")
    private Double airRight500;

    @Excel(name = "右耳气导1000Hz")
    @ApiModelProperty(value = "气导右耳1000Hz")
    private Double airRight1000;

    @Excel(name = "右耳气导2000Hz")
    @ApiModelProperty(value = "气导右耳2000Hz")
    private Double airRight2000;

    @Excel(name = "右耳气导3000Hz")
    @ApiModelProperty(value = "气导右耳3000Hz")
    private Double airRight3000;

    @Excel(name = "右耳气导4000Hz")
    @ApiModelProperty(value = "气导右耳4000Hz")
    private Double airRight4000;

    @Excel(name = "右耳气导6000Hz")
    @ApiModelProperty(value = "气导右耳6000Hz")
    private Double airRight6000;

    @Excel(name = "噪声双耳高频平均听阈（校正值）")
    @ApiModelProperty(value = "电测听")
    private String dct;

    @Excel(name = "心电图")
    @ApiModelProperty(value = "心电图")
    private String xdt;


    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "胸片")
    @ApiModelProperty(value = "胸片")
    private String xp;

    @Excel(name = "血常规白细胞计数（WBC）")
    @ApiModelProperty(value = "血常规白细胞计数（WBC）")
    private String bxbjs;

    @Excel(name = "血常规红细胞计数（RBC）")
    @ApiModelProperty(value = "血常规红细胞计数（RBC）")
    private String hxb;


    @Excel(name = "血常规血红蛋白（Hb）")
    @ApiModelProperty(value = "血常规血红蛋白（Hb）")
    private String xhdb;

    @Excel(name = "血常规血小板计数（PLT）")
    @ApiModelProperty(value = "血常规血小板计数（PLT）")
    private String xxb;


    @Excel(name = "尿比重")
    @ApiModelProperty(value = "尿比重")
    private String nbz;

    @Excel(name = "尿常规白细胞（WBC）")
    @ApiModelProperty(value = "尿常规白细胞（WBC）")
    private String bxb;

    @Excel(name = "尿常规尿蛋白（PRO）")
    @ApiModelProperty(value = "尿常规尿蛋白（PRO）")
    private String ndb;

    @Excel(name = "尿常规尿潜血（BLD）")
    @ApiModelProperty(value = "尿常规尿潜血（BLD）")
    private String nqx;

    @Excel(name = "镜检红细胞")
    @ApiModelProperty(value = "镜检红细胞")
    private String jjhxb;

    @Excel(name = "尿常规尿糖（GLU）")
    @ApiModelProperty(value = "尿常规尿糖（GLU）")
    private String nt;

    @Excel(name = "肝功能谷丙转氨酶（ALT）")
    @ApiModelProperty(value = "肝功能谷丙转氨酶（ALT）")
    private String alt;

    @Excel(name = "空腹血糖")
    @ApiModelProperty(value = "空腹血糖")
    private String kfxt;

    @Excel(name = "血铅")
    @ApiModelProperty(value = "血铅")
    private String xq;

    @Excel(name = "尿铅")
    @ApiModelProperty(value = "尿铅")
    private String nq;

    @Excel(name = "铅红细胞锌原卟啉（ZPP）")
    @ApiModelProperty(value = "铅红细胞锌原卟啉（ZPP）")
    private String zpp;


    @Excel(name = "苯血常规（中性粒细胞）")
    @ApiModelProperty(value = "苯血常规（中性粒细胞）")
    private String zxlxb;

    @Excel(name = "布鲁菌属虎红缓冲液玻片凝集实验（RPBT）")
    @ApiModelProperty(value = "布鲁菌属虎红缓冲液玻片凝集实验（RPBT）")
    private String rpbt;

    @Excel(name = "布鲁菌属试管凝集反应（Wright）(1:100)")
    @ApiModelProperty(value = "布鲁菌属试管凝集反应（Wright）(1:100)")
    private String Wright;
}
