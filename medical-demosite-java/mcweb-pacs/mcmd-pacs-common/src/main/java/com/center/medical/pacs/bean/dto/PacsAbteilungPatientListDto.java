package com.center.medical.pacs.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * pacs体检者列表分页
 * @author xhp
 * @since 2023-03-16 8:48
 */
@Data
public class PacsAbteilungPatientListDto {
    @ApiModelProperty(value = "体检者id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "0男 1女 2通用")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "体检者类型名称 1普通会员 2VIP 3VVIP")
    private String idPatientClass;

    @ApiModelProperty(value = "体检类型id")
    private String idExamtype;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "0未审  1已审")
    private Integer status;

    @ApiModelProperty(value = "所有收费项目名称，逗号拼接")
    private String exams;

    @ApiModelProperty(value = "图片数量")
    private Integer img;

    @ApiModelProperty(value = "0无图 1有图")
    private Integer jcstatus;

    @ApiModelProperty(value = "单位名称")
    private String orgName;

    @ApiModelProperty(value = "dicom图片集合，多个以英文逗号(,)隔开")
    private String dicomImgs;


}
