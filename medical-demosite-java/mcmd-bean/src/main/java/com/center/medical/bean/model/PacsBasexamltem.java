package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-基础检查项(PacsBasexamltem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_basexamltem")
@ApiModel(value = "PacsBasexamltem", description = "PACS-基础检查项实体类")
public class PacsBasexamltem extends Model<PacsBasexamltem> implements Serializable {
    private static final long serialVersionUID = 728652076302286322L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "系统维护标志")
    private String sysmanmark;

    @ApiModelProperty(value = "键值")
    private String keyexamitem;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "检查项目名称缩写")
    private String examitemNameabr;

    @ApiModelProperty(value = "打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "检查项目英文名称")
    private String examitemNameeng;

    @ApiModelProperty(value = "检查项目名称英文缩写")
    private String examitemNameengabr;

    @ApiModelProperty(value = "检查项目代码")
    private String examitemCode;

    @ApiModelProperty(value = "examitemCode2")
    private String examitemCode2;

    @ApiModelProperty(value = "examitemCode3")
    private String examitemCode3;

    @ApiModelProperty(value = "examitemCodehm")
    private String examitemCodehm;

    @ApiModelProperty(value = "examitemcodex")
    private String examitemcodex;

    @ApiModelProperty(value = "lbdmExamitemtype")
    private String lbdmExamitemtype;

    @ApiModelProperty(value = "项目类型ID")
    private String idExamitemtype;

    @ApiModelProperty(value = "idTreegroup")
    private String idTreegroup;

    @ApiModelProperty(value = "idTreegroupsub")
    private String idTreegroupsub;

    @ApiModelProperty(value = "值类型")
    private String valuetype;

    @ApiModelProperty(value = "值单位")
    private String valueunit;

    @ApiModelProperty(value = "多行文本行数")
    private Integer valuememolines;

    @ApiModelProperty(value = "自动计算：0或null.否 1.是")
    private Integer fAutocalc;

    @ApiModelProperty(value = "是否打印：0或null.否 1.是")
    private Integer fPrint;

    @ApiModelProperty(value = "是否多值：0或null.否 1.是")
    private Integer fMultival;

    @ApiModelProperty(value = "通用组(不再使用)")
    private Integer commongroup;

    @ApiModelProperty(value = "多值列数")
    private Integer valuecheckcols;

    @ApiModelProperty(value = "仅自由输入")
    private Integer fEntryonly;

    @ApiModelProperty(value = "无自由输入")
    private Integer fNoentry;

    @ApiModelProperty(value = "输入至小结== 描述进小结")
    private Integer fEntrytoconclusion;

    @ApiModelProperty(value = "使用勾选")
    private Integer fUsecheck;

    @ApiModelProperty(value = "同行显示")
    private Integer fSameline;

    @ApiModelProperty(value = "收费项目中可重复")
    private Integer fCanDup;

    @ApiModelProperty(value = "检验手工项目")
    private Integer fLabByhand;

    @ApiModelProperty(value = "检验不必等待")
    private Integer fLabNowait;

    @ApiModelProperty(value = "小结级别")
    private Integer conclusionlevel;

    @ApiModelProperty(value = "小结标题 ==  名称进小结")
    private Integer fSummarytitle;

    @ApiModelProperty(value = "高低用词(不再使用)")
    private String phraseforhilo;

    @ApiModelProperty(value = "数值型增量")
    private Integer increments;

    @ApiModelProperty(value = "精度")
    private Integer numprecision;

    @ApiModelProperty(value = "验证最小值")
    private Integer valuevalidmin;

    @ApiModelProperty(value = "验证最大值")
    private Integer valuevalidmax;

    @ApiModelProperty(value = "itemflagcaselessthan")
    private String itemflagcaselessthan;

    @ApiModelProperty(value = "itemflagcasegreaterthan")
    private String itemflagcasegreaterthan;

    @ApiModelProperty(value = "数字文字同时显示")
    private Integer fBothnumtext;

    @ApiModelProperty(value = "FNumonly")
    private Integer fNumonly;

    @ApiModelProperty(value = "应用范围")
    private Integer fUseappmaxmin;

    @ApiModelProperty(value = "含低值")
    private Integer fIncludemin;

    @ApiModelProperty(value = "含高值")
    private Integer fIncludemax;

    @ApiModelProperty(value = "性别：0.代表男 1.代表女 2.通用")
    private Integer fMale;

    @ApiModelProperty(value = "男性缺省值")
    private String valuemaledef;

    @ApiModelProperty(value = "男性上限")
    private Integer valuemalemax;

    @ApiModelProperty(value = "valuemalenormmid")
    private Integer valuemalenormmid;

    @ApiModelProperty(value = "男性下限")
    private Integer valuemalemin;

    @ApiModelProperty(value = "valuemaleweakup")
    private Integer valuemaleweakup;

    @ApiModelProperty(value = "valuemaleweakdown")
    private Integer valuemaleweakdown;

    @ApiModelProperty(value = "男性生命值上限")
    private Integer valuemalesevereup;

    @ApiModelProperty(value = "男性生命值下限")
    private Integer valuemaleseveredown;

    @ApiModelProperty(value = "用于女性：0.代表男 1.代表女 2.通用")
    private Integer fFemale;

    @ApiModelProperty(value = "女性缺省值")
    private String valuefemaledef;

    @ApiModelProperty(value = "女性上限")
    private Integer valuefemalemax;

    @ApiModelProperty(value = "valuefemalenormmid")
    private Integer valuefemalenormmid;

    @ApiModelProperty(value = "女性下限")
    private Integer valuefemalemin;

    @ApiModelProperty(value = "valuefemaleweakup")
    private Integer valuefemaleweakup;

    @ApiModelProperty(value = "valuefemaleweakdown")
    private Integer valuefemaleweakdown;

    @ApiModelProperty(value = "女性生命值上限")
    private Integer valuefemalesevereup;

    @ApiModelProperty(value = "女性生命值下限")
    private Integer valuefemaleseveredown;

    @ApiModelProperty(value = "gxdm")
    private String gxdm;

    @ApiModelProperty(value = "表达式")
    private String expression;

    @ApiModelProperty(value = "结论词(高)")
    private String idConclusionhi;

    @ApiModelProperty(value = "结论词(低)")
    private String idConclusionlo;

    @ApiModelProperty(value = "结论词(阳)")
    private String idConclusionpo;

    @ApiModelProperty(value = "结论词(阴)")
    private String idConclusionne;

    @ApiModelProperty(value = "idConclusionweaknormhi")
    private Integer idConclusionweaknormhi;

    @ApiModelProperty(value = "idConclusionweaknormlo")
    private Integer idConclusionweaknormlo;

    @ApiModelProperty(value = "idConclusionweakposihi")
    private Integer idConclusionweakposihi;

    @ApiModelProperty(value = "idConclusionweakposilo")
    private Integer idConclusionweakposilo;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "inputcodeb")
    private String inputcodeb;

    @ApiModelProperty(value = "inputcodec")
    private String inputcodec;

    @ApiModelProperty(value = "inputcoded")
    private String inputcoded;

    @ApiModelProperty(value = "inputcodee")
    private String inputcodee;

    @ApiModelProperty(value = "FPatientbaseinfo")
    private Integer fPatientbaseinfo;

    @ApiModelProperty(value = "patientbaseinfodisporder")
    private Integer patientbaseinfodisporder;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "行序")
    private String disporder;

    @ApiModelProperty(value = "FExamitemexportflag01")
    private Integer fExamitemexportflag01;

    @ApiModelProperty(value = "FExamitemexportflag02")
    private Integer fExamitemexportflag02;

    @ApiModelProperty(value = "FNodepartsummary")
    private Integer fNodepartsummary;

    @ApiModelProperty(value = "FComparereport")
    private Integer fComparereport;

    @ApiModelProperty(value = "FNoreportoutput")
    private Integer fNoreportoutput;

    @ApiModelProperty(value = "FAlwayspositive")
    private Integer fAlwayspositive;

    @ApiModelProperty(value = "FTitleonreport")
    private Integer fTitleonreport;

    @ApiModelProperty(value = "itemrefrange")
    private String itemrefrange;

    @ApiModelProperty(value = "keysummarygroup")
    private String keysummarygroup;

    @ApiModelProperty(value = "FUseapprangeoccudisease")
    private Integer fUseapprangeoccudisease;

    @ApiModelProperty(value = "expressionoccudisease")
    private Integer expressionoccudisease;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "类型：0.健康检查类型 1.职业检查类型 2.健康+职业")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "接口代码")
    private String interfaceCode;

    @ApiModelProperty(value = "危急值上限")
    private Double valuedangerousmax;

    @ApiModelProperty(value = "危急值下限")
    private Double valuedangerousmin;

    @ApiModelProperty(value = "描述进小结：0或null.否 1.是")
    private Integer isDesc;

    @ApiModelProperty(value = "名称进小结：0或null.否 1.是")
    private Integer isName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的：0或null.否 1.是")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;
}
