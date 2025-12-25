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
 * 同步主表(TbPatient)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_tb_patient")
@ApiModel(value = "TbPatient", description = "同步主表实体类")
public class TbPatient extends Model<TbPatient> implements Serializable {
    private static final long serialVersionUID = -38864074174871987L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检者类型ID PatienType表")
    private String idPatientclass;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "短体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "DICOM图片路径（多个路径用 分号+空格  拼接）")
    private String pictures;

    @ApiModelProperty(value = "上传分中心ID")
    private String upFzxId;

    @ApiModelProperty(value = "目标分中心ID")
    private String downFzxId;

    @ApiModelProperty(value = "上传人用户名")
    private String upName;

    @ApiModelProperty(value = "目标分中心下载时间")
    private Date downTime;

    @ApiModelProperty(value = "目标分中心上传时间")
    private Date backTime;

    @ApiModelProperty(value = "下载时间")
    private Date finishTime;

    @ApiModelProperty(value = "状态  1已上传 2目标分中心已下载 3目标分中心已上传  4已下载")
    private Integer status;

    @ApiModelProperty(value = "目标分中心下载人用户名")
    private String downName;

    @ApiModelProperty(value = "目标分中心上传人用户名")
    private String backName;

    @ApiModelProperty(value = "下载人用户名")
    private String finishName;

    @ApiModelProperty(value = "体检类型ID")
    private Integer idExamtype;
}
