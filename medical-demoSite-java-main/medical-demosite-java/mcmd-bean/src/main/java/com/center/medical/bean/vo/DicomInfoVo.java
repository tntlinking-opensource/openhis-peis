package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Dicom文件信息
 */
@Data
@ApiModel(value = "Dicom文件信息")
public class DicomInfoVo implements Serializable {
    private static final long serialVersionUID = -1885881052361902681L;

    @ApiModelProperty("部位")
    public String enName;

    @ApiModelProperty("客户姓名")
    private String patientName;

    @ApiModelProperty("体检号")
    private String patientcode;

    @ApiModelProperty("客户性别")
    private String patientSex;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("机构名称")
    private String institutionName;

    @ApiModelProperty("图片生成日期")
    private String contentDate;

    @ApiModelProperty("图片生成时间")
    private String contentTime;

    @ApiModelProperty("磁场强度")
    private String mag;

    @ApiModelProperty("客户方向：右上角大写的L")
    private String patienOrientation;

    @ApiModelProperty("检查部位")
    private String bodyPart;

    @ApiModelProperty("检查协议名称")
    private String protocolName;

    @ApiModelProperty("视图位置：右上角大写的pa")
    private String viewPosition;

//    @ApiModelProperty("模态")
//    private String modality;

//    @ApiModelProperty("制造商")
//    private String manufacturer;

//    @ApiModelProperty("系列描述")
//    private String seriesDesc;

//    @ApiModelProperty("部门名称")
//    private String institutionalDeptName;

//    @ApiModelProperty("曝光量")
//    private String exposure;

//    @ApiModelProperty("曝光指数")
//    private String exposureIndex;

    @ApiModelProperty("窗位：L")
    private String windowCenter;

    @ApiModelProperty("窗宽：W")
    private String windowWidth;

    @ApiModelProperty("曝光时间")
    private String exposureTime;

    @ApiModelProperty("电压峰值")
    private String kv;

    @ApiModelProperty("电压峰值")
    private String ma;

    @ApiModelProperty("接口类型")
    private String jklxs;

    @ApiModelProperty("文件名称")
    private String sOPInstanceUID;
}
