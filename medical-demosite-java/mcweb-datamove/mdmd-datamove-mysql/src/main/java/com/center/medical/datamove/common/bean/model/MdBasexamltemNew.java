package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * JC检查项目表(MdBasexamltemNew)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
@Data
@TableName("md_basexamltem_new")
@ApiModel(value = "MdBasexamltemNew", description = "JC检查项目表实体类")
public class MdBasexamltemNew extends Model<MdBasexamltemNew> implements Serializable {
    private static final long serialVersionUID = -81701078299076737L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

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

    @ApiModelProperty(value = "艾迪康代码")
    private String examitemCode3;

    @ApiModelProperty(value = "检验标示重置")
    private String examitemCodehm;

    @ApiModelProperty(value = "项目类型ID")
    private String idExamitemtype;

    @ApiModelProperty(value = "山东省职业健康（含放射）共享数据集规范 检查结果类型代码 01 数值型、02 字符型、03 枚举型")
    private String valuetype;

    @ApiModelProperty(value = "山东省职业健康（含放射）共享数据集规范  检查项目计量单位")
    private String valueunit;

    @ApiModelProperty(value = "仅自由输入")
    private Integer fEntryonly;

    @ApiModelProperty(value = "收费项目中可重复")
    private Integer fCanDup;

    @ApiModelProperty(value = "检验手工项目")
    private Integer fLabByhand;

    @ApiModelProperty(value = "检验不必等待")
    private Integer fLabNowait;

    @ApiModelProperty(value = "用于男性")
    private Integer fMale;

    @ApiModelProperty(value = "男性上限")
    private Object valuemalemax;

    @ApiModelProperty(value = "男性下限")
    private Object valuemalemin;

    @ApiModelProperty(value = "用于女性")
    private Integer fFemale;

    @ApiModelProperty(value = "女性上限")
    private Object valuefemalemax;

    @ApiModelProperty(value = "女性下限")
    private Object valuefemalemin;

    @ApiModelProperty(value = "结论词(高)")
    private String idConclusionhi;

    @ApiModelProperty(value = "结论词(低)")
    private String idConclusionlo;

    @ApiModelProperty(value = "结论词(阳)")
    private String idConclusionpo;

    @ApiModelProperty(value = "结论词(阴)")
    private String idConclusionne;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "行序")
    private String disporder;

    @ApiModelProperty(value = "是否外送: null或0.不是1.是（各中心自己设置，同步时排除此字段）")
    private Integer expressionoccudisease;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "0:健康检查类型1:职业检查类型2:健康+职业(职业)")
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
}
