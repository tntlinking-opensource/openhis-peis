package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 噗噗管(MdPufftube)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Data
@TableName("md_pufftube")
@ApiModel(value = "MdPufftube", description = "噗噗管实体类")
public class MdPufftube extends Model<MdPufftube> implements Serializable {
    private static final long serialVersionUID = 606024482812778279L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "trontimage")
    private String trontimage;

    @ApiModelProperty(value = "sideimage")
    private String sideimage;

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "是否更新")
    private String isUpdate;

    @ApiModelProperty(value = "md5")
    private String md5;
}
