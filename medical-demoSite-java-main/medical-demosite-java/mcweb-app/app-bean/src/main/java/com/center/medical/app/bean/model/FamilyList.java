package com.center.medical.app.bean.model;


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
 * 家人列表(FamilyList)表实体类
 *
 * @author ay
 * @since 2024-03-13 14:31:01
 */
@Data
@TableName("md_family_list")
@ApiModel(value = "FamilyList", description = "家人列表实体类")
public class FamilyList extends Model<FamilyList> implements Serializable {
    private static final long serialVersionUID = 665936727324301050L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "本系统userId")
    private String userId;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "身份证号")
    private String idcardno;


    @ApiModelProperty(value = "性别ID")
    private Integer idSex;


    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "客户证件类型")
    private Integer countreportoccupationxml;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否是从线上系统插入的 1是0否")
    private Integer fromOnline;

}
