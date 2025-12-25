package com.center.medical.datamove.oracle.bean.model;


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
 * LIS接收结果(LisResult)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:38
 */
@Data
@TableName("LIS_RESULT")
@ApiModel(value = "LisResult", description = "LIS接收结果实体类")
public class LisResult extends Model<LisResult> implements Serializable {
    private static final long serialVersionUID = 472692922069192609L;

    @ApiModelProperty(value = "修改日期")
    private Date modifyDate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "信息来源")
    private String sources;

    @ApiModelProperty(value = "检查日期")
    private Date examTime;

    @ApiModelProperty(value = "采样时间")
    private Date samplingTime;

    @ApiModelProperty(value = "送检时间")
    private Date inspectionTime;

    @ApiModelProperty(value = "报告日期")
    private Date reportDate;

    @ApiModelProperty(value = "体检号")
    private String examinerNumber;

    @ApiModelProperty(value = "体检者姓名")
    private String examinerName;

    @ApiModelProperty(value = "体检者性别 * 可选项 （男，女）")
    private String examinerSex;

    @ApiModelProperty(value = "体检者年龄")
    private String examinerAge;

    @ApiModelProperty(value = "体检者生日")
    private Date examinerBirthday;

    @ApiModelProperty(value = "采样项目接口代码（接口项目代码）")
    private String interfaceCode;

    @ApiModelProperty(value = "检验目的")
    private String inspectionPurpose;

    @ApiModelProperty(value = "仪器代号")
    private String instrumentCode;

    @ApiModelProperty(value = "样本号")
    private String sampleNumber;

    @ApiModelProperty(value = "项目代号")
    private String itemCode;

    @ApiModelProperty(value = "打印序号")
    private String xid;

    @ApiModelProperty(value = "报告项目名称（打印在纸上的项目名称）")
    private String reportItemName;

    @ApiModelProperty(value = "报告结果")
    private String value;

    @ApiModelProperty(value = "参考值")
    private String reference;

    @ApiModelProperty(value = "参考上限")
    private String maxReference;

    @ApiModelProperty(value = "参考下限")
    private String minReference;

    @ApiModelProperty(value = "项目单位")
    private String unit;

    @ApiModelProperty(value = "高低标志 * 可选项 （0：正常，1：高，2：低）")
    private String flag;

    @ApiModelProperty(value = "科室代码")
    private String departmentCode;

    @ApiModelProperty(value = "建议")
    private String suggestion;

    @ApiModelProperty(value = "辅助建议")
    private String supportingSuggestions;

    @ApiModelProperty(value = "诊断")
    private String diagnosis;

    @ApiModelProperty(value = "样本类型")
    private String sampleType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检查医师CODe")
    private String doctorCode;

    @ApiModelProperty(value = "检查医师姓名")
    private String doctorName;

    @ApiModelProperty(value = "审核医师CODE")
    private String reviewCode;

    @ApiModelProperty(value = "审核医师姓名")
    private String reviewName;
}
