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
 * 职业团检分析-危害因素(MdFxHarm)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Data
@TableName("md_fx_harm")
@ApiModel(value = "MdFxHarm", description = "职业团检分析-危害因素实体类")
public class MdFxHarm extends Model<MdFxHarm> implements Serializable {
    private static final long serialVersionUID = -71720768575029233L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素id")
    private String harmId;

    @ApiModelProperty(value = "样本id")
    private String sampleId;

    @ApiModelProperty(value = "人数")
    private Integer personNum;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;
}
