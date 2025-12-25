package com.center.medical.bean.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * 噗噗管(Pufftube)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:10
 */
@Data
@TableName("md_pufftube")
@ApiModel(value = "Pufftube", description = "噗噗管实体类")
public class Pufftube extends Model<Pufftube> implements Serializable {
    private static final long serialVersionUID = -47084969108123258L;

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
