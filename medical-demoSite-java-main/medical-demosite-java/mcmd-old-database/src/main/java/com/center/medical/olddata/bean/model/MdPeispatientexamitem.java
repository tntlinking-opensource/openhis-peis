package com.center.medical.olddata.bean.model;


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
 * LIS结果(LisPacs数据)(MdPeispatientexamitem)表实体类
 *
 * @author ay
 * @since 2024-06-05 15:19:03
 */
@Data
@TableName("md_peispatientexamitem")
@ApiModel(value = "MdPeispatientexamitem", description = "LIS结果(LisPacs数据)实体类")
public class MdPeispatientexamitem extends Model<MdPeispatientexamitem> implements Serializable {
    private static final long serialVersionUID = 552749672677050019L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "关联表名")
    private String associativeTable;


    @ApiModelProperty(value = "ID_PATIENTEXAMITEM")
    private Double idPatientexamitem;


    @ApiModelProperty(value = "体检者检查分科ID(没用)")
    private Double idPatientexamdepart;


    @ApiModelProperty(value = "体检者收费项目ID(没用)")
    private Double idPatientfeeitem;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;


    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitem;


    @ApiModelProperty(value = "检查项目ID")
    private String idExamitem;


    @ApiModelProperty(value = "检查项目名称(冗余)（名称）")
    private String examitemNameR;


    @ApiModelProperty(value = "检查项目编码(冗余)(LIS接口代码)")
    private String examitemCodeR;


    @ApiModelProperty(value = "检查体检描述ID")
    private String idExamitemvalue;


    @ApiModelProperty(value = "重症级别")
    private Integer severedegree;


    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;


    @ApiModelProperty(value = "检查项目体征词")
    private String examitemvalues;


    @ApiModelProperty(value = "检查项目体征: 描述")
    private String examitemvaluestext;


    @ApiModelProperty(value = "检查项目体征: 简单描述(结果)")
    private String examitemvaluesshort;


    @ApiModelProperty(value = "检查项目体征: 数字")
    private String examitemvaluesnumber;


    @ApiModelProperty(value = "检查项目标志（标志）")
    private String labitemflag;


    @ApiModelProperty(value = "是否 正常")
    private Integer fLabitemnormal;


    @ApiModelProperty(value = "检查项目体征: 数字2")
    private String examitemvaluesnumber2;


    @ApiModelProperty(value = "检查项目体征: 数字3")
    private String examitemvaluesnumber3;


    @ApiModelProperty(value = "收费项目中检查项目行序")
    private String befidDisporderR;


    @ApiModelProperty(value = "创建时间")
    private Date rowcreatetime;


    @ApiModelProperty(value = "LIS结果已收(不使用)")
    private String fLabrcvdfromlis;


    @ApiModelProperty(value = "VALUEOPER")
    private String valueoper;


    @ApiModelProperty(value = "VALUEOPER2")
    private String valueoper2;


    @ApiModelProperty(value = "创建时间1")
    private Date createdate;


    @ApiModelProperty(value = "修改时间")
    private Date modifydate;


    @ApiModelProperty(value = "状态")
    private String status;


    @ApiModelProperty(value = "LIS代码")
    private String lisCode;


    @ApiModelProperty(value = "单位")
    private String units;


    @ApiModelProperty(value = "科室ID")
    private String depId;


    @ApiModelProperty(value = "报告结果")
    private String reportRange;


    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;


    @ApiModelProperty(value = "体检者姓名")
    private String patientName;


    @ApiModelProperty(value = "检查医师")
    private String examDoctor;


    @ApiModelProperty(value = "检查时间")
    private Date examDateTime;


    @ApiModelProperty(value = "图片路径")
    private String imageFullPath;


    @ApiModelProperty(value = "审核人")
    private String auditName;


    @ApiModelProperty(value = "检查人")
    private String inspectName;


    @ApiModelProperty(value = "审核时间")
    private Date auditDate;


    @ApiModelProperty(value = "LIS范围/报告范围 低")
    private Double reflow;


    @ApiModelProperty(value = "LIS范围/报告范围 高")
    private Double refhigh;


    @ApiModelProperty(value = "LIS样本编号")
    private Double lisybbh;


    @ApiModelProperty(value = "关联表数据")
    private String tableValue;


    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;


    @ApiModelProperty(value = "健康小结")
    private String conclusions;


    @ApiModelProperty(value = "检查结果子表数据")
    private String sectionResultTwoData;


    @ApiModelProperty(value = "描述")
    private String ms;


    @ApiModelProperty(value = "输入结果")
    private String inputResult;


    @ApiModelProperty(value = "弃检")
    private Integer isUnchecked;


    @ApiModelProperty(value = "结伦词ID")
    private String basconclusionId;


    @ApiModelProperty(value = "是否阳性结果")
    private Integer positive;


    @ApiModelProperty(value = "检查项目打印名称")
    private String examitemNameprn;


    @ApiModelProperty(value = "收费项目")
    private String examfeeitemNameprn;


    @ApiModelProperty(value = "检查类型：0:健康检查类型 1:职业检查类型 2:健康+职业(职业)")
    private Integer type;


    @ApiModelProperty(value = "是否阳性结果")
    private Integer posistive;


    @ApiModelProperty(value = "职业状态")
    private String zyStatus;


    @ApiModelProperty(value = "检查医师代码")
    private String inspectCode;


    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;


    @ApiModelProperty(value = "收样时间（虹桥lis）")
    private Date receiveDate;


    @ApiModelProperty(value = "艾迪康代码")
    private String adiconCode;

}
