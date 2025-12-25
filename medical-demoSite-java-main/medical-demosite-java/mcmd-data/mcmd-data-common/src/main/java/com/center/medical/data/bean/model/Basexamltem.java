package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * JC检查项目表(Basexamltem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_basexamltem")
@ApiModel(value = "Basexamltem", description = "JC检查项目表实体类")
public class Basexamltem extends Model<Basexamltem> implements Serializable {
    private static final long serialVersionUID = -41781898311873001L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "系统维护标志")
    private String sysmanmark;

    @ApiModelProperty(value = "KEYEXAMITEM")
    private String keyexamitem;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "检查项目名称缩写")
    private String examitemNameabr;

    @ApiModelProperty(value = "检查项目打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "检查项目英文名称")
    private String examitemNameeng;

    @ApiModelProperty(value = "检查项目英文名称缩写")
    private String examitemNameengabr;

    @ApiModelProperty(value = "检查项目代码")
    private String examitemCode;

    @ApiModelProperty(value = "EXAMITEM_CODE2")
    private String examitemCode2;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "EXAMITEM_CODE3")
    private String examitemCode3;

    @ApiModelProperty(value = "EXAMITEM_CODEHM")
    private String examitemCodehm;

    @ApiModelProperty(value = "导出代码")
    private String examitemcodex;

    @ApiModelProperty(value = "_LBDM_EXAMITEMTYPE")
    private String lbdmExamitemtype;

    @ApiModelProperty(value = "项目类型ID")
    private String idExamitemtype;

    @ApiModelProperty(value = "ID_TREEGROUP")
    private String idTreegroup;

    @ApiModelProperty(value = "ID_TREEGROUPSUB")
    private String idTreegroupsub;

    @ApiModelProperty(value = "值类型")
    private String valuetype;

    @ApiModelProperty(value = "值单位")
    private String valueunit;

    @ApiModelProperty(value = "多行文本行数")
    private Integer valuememolines;

    @ApiModelProperty(value = "自动计算")
    private Integer fAutocalc;

    @ApiModelProperty(value = "是否打印")
    private Integer fPrint;

    @ApiModelProperty(value = "是否多值")
    private Integer fMultival;

    @ApiModelProperty(value = "通用组(不再使用)")
    private Integer commongroup;

    @ApiModelProperty(value = "多值列数")
    private Integer valuecheckcols;

    @ApiModelProperty(value = "仅自由输入")
    private Integer fEntryonly;

    @ApiModelProperty(value = "无自由输入")
    private Integer fNoentry;

    @ApiModelProperty(value = "输入至小结")
    private Integer fEntrytoconclusion;

    @ApiModelProperty(value = "使用勾选")
    private Integer fUsecheck;

    @ApiModelProperty(value = "同行显示")
    private Integer fSameline;

    @ApiModelProperty(value = "可重复(不再使用)")
    private Integer fCanDup;

    @ApiModelProperty(value = "检验手工项目")
    private Integer fLabByhand;

    @ApiModelProperty(value = "检验不必等待")
    private Integer fLabNowait;

    @ApiModelProperty(value = "小结级别")
    private Integer conclusionlevel;

    @ApiModelProperty(value = "小结标题")
    private Integer fSummarytitle;

    @ApiModelProperty(value = "高低用词(不再使用)")
    private String phraseforhilo;

    @ApiModelProperty(value = "数值型增量")
    private Long increments;

    @ApiModelProperty(value = "精度")
    private Long numprecision;

    @ApiModelProperty(value = "VALUEVALIDMIN")
    private Long valuevalidmin;

    @ApiModelProperty(value = "VALUEVALIDMAX")
    private Long valuevalidmax;

    @ApiModelProperty(value = "ITEMFLAGCASELESSTHAN")
    private String itemflagcaselessthan;

    @ApiModelProperty(value = "ITEMFLAGCASEGREATERTHAN")
    private String itemflagcasegreaterthan;

    @ApiModelProperty(value = "数字文字同时显示")
    private Integer fBothnumtext;

    @ApiModelProperty(value = "F_NUMONLY")
    private Integer fNumonly;

    @ApiModelProperty(value = "应用范围")
    private Integer fUseappmaxmin;

    @ApiModelProperty(value = "含低值")
    private Integer fIncludemin;

    @ApiModelProperty(value = "含高值")
    private Integer fIncludemax;

    @ApiModelProperty(value = "性别：0男性 1女性 2通用")
    private Integer fMale;

    @ApiModelProperty(value = "男性缺省值")
    private String valuemaledef;

    @ApiModelProperty(value = "男性上限")
    private Object valuemalemax;

    @ApiModelProperty(value = "VALUEMALENORMMID")
    private Long valuemalenormmid;

    @ApiModelProperty(value = "男性下限")
    private Object valuemalemin;

    @ApiModelProperty(value = "VALUEMALEWEAKUP")
    private Long valuemaleweakup;

    @ApiModelProperty(value = "VALUEMALEWEAKDOWN")
    private Long valuemaleweakdown;

    @ApiModelProperty(value = "男性生命值上限")
    private Long valuemalesevereup;

    @ApiModelProperty(value = "男性生命值下限")
    private Long valuemaleseveredown;

    @ApiModelProperty(value = "用于女性")
    private Integer fFemale;

    @ApiModelProperty(value = "女性缺省值")
    private String valuefemaledef;

    @ApiModelProperty(value = "女性上限")
    private Object valuefemalemax;

    @ApiModelProperty(value = "VALUEFEMALENORMMID")
    private Long valuefemalenormmid;

    @ApiModelProperty(value = "女性下限")
    private Object valuefemalemin;

    @ApiModelProperty(value = "VALUEFEMALEWEAKUP")
    private Long valuefemaleweakup;

    @ApiModelProperty(value = "VALUEFEMALEWEAKDOWN")
    private Long valuefemaleweakdown;

    @ApiModelProperty(value = "女性生命值上限")
    private Long valuefemalesevereup;

    @ApiModelProperty(value = "女性生命值下限")
    private Long valuefemaleseveredown;

    @ApiModelProperty(value = "_GXDM")
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

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKNORMHI")
    private Integer idConclusionweaknormhi;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKNORMLO")
    private Integer idConclusionweaknormlo;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKPOSIHI")
    private Integer idConclusionweakposihi;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKPOSILO")
    private Long idConclusionweakposilo;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "INPUTCODEB")
    private String inputcodeb;

    @ApiModelProperty(value = "INPUTCODEC")
    private String inputcodec;

    @ApiModelProperty(value = "INPUTCODED")
    private String inputcoded;

    @ApiModelProperty(value = "INPUTCODEE")
    private String inputcodee;

    @ApiModelProperty(value = "(不使用)")
    private Integer fPatientbaseinfo;

    @ApiModelProperty(value = "(不使用 )")
    private Integer patientbaseinfodisporder;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "行序")
    private String disporder;

    @ApiModelProperty(value = "F_EXAMITEMEXPORTFLAG01")
    private Integer fExamitemexportflag01;

    @ApiModelProperty(value = "F_EXAMITEMEXPORTFLAG02")
    private Integer fExamitemexportflag02;

    @ApiModelProperty(value = "F_NODEPARTSUMMARY")
    private Integer fNodepartsummary;

    @ApiModelProperty(value = "F_COMPAREREPORT")
    private Integer fComparereport;

    @ApiModelProperty(value = "F_NOREPORTOUTPUT")
    private Integer fNoreportoutput;

    @ApiModelProperty(value = "F_ALWAYSPOSITIVE")
    private Integer fAlwayspositive;

    @ApiModelProperty(value = "F_TITLEONREPORT")
    private Integer fTitleonreport;

    @ApiModelProperty(value = "ITEMREFRANGE")
    private String itemrefrange;

    @ApiModelProperty(value = "KEYSUMMARYGROUP")
    private String keysummarygroup;

    @ApiModelProperty(value = "F_USEAPPRANGEOCCUDISEASE")
    private Integer fUseapprangeoccudisease;

    @ApiModelProperty(value = "是否外送: null或0.不是1.是（各中心自己设置，同步时排除此字段）")
    private Integer expressionoccudisease;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0:健康检查类型1:职业检查类型2:健康+职业(职业)")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "接口代码")
    private String interfaceCode;

    @ApiModelProperty(value = "危急值上限")
    private Object valuedangerousmax;

    @ApiModelProperty(value = "男性生命值下限")
    private Object valuedangerousmin;

    @ApiModelProperty(value = "描述进小结")
    private Integer isDesc;

    @ApiModelProperty(value = "名称进小结")
    private Integer isName;

    @ApiModelProperty(value = "0：解锁 1：锁定")
    private Integer status;

    @ApiModelProperty(value = "0:未删除 1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "${column.comment}")
    private String devicetypePositionCheckitem;

    @ApiModelProperty(value = "${column.comment}")
    private Integer addUnit;

    @ApiModelProperty(value = "1：lis结果只要不是未见或阴性就要算做异常")
    private Integer itemFlag;


    @TableField(exist = false)
    @ApiModelProperty(value = "结论词(高)")
    private String idConclusionhiName;

    @TableField(exist = false)
    @ApiModelProperty(value = "结论词(低)")
    private String idConclusionloName;

    @TableField(exist = false)
    @ApiModelProperty(value = "结论词(阳)")
    private String idConclusionpoName;

    @TableField(exist = false)
    @ApiModelProperty(value = "结论词(阴)")
    private String idConclusionneName;

    @TableField(exist = false)
    @ApiModelProperty(value = "检查类型名称")
    private String examitemtypeName;

    @TableField(exist = false)
    @ApiModelProperty(value = "科室名称")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "关联的体征词列表")
    private List<BasexamltemSign> basexamltemSignList;

    @TableField(exist = false)
    @ApiModelProperty(value = "cid,跟id一样")
    private String cid;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String fzxName;

    @TableField(exist = false)
    @ApiModelProperty(value = "艾迪康名称")
    private String adiconName;


}
