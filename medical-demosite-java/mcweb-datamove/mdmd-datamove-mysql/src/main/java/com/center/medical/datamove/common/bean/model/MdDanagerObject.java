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
 * TJ危害因素收费项目(MdDanagerObject)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Data
@TableName("md_danager_object")
@ApiModel(value = "MdDanagerObject", description = "TJ危害因素收费项目实体类")
public class MdDanagerObject extends Model<MdDanagerObject> implements Serializable {
    private static final long serialVersionUID = -39633701542576117L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素分类名称")
    private String harmTypeName;

    @ApiModelProperty(value = "危害因素分类ID")
    private String harmTypeId;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素名称ID")
    private String harmId;

    @ApiModelProperty(value = "收费项目名称")
    private String items;

    @ApiModelProperty(value = "团体ID")
    private String sellcustomerId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "应查人数")
    private Integer peopleNum;

    @ApiModelProperty(value = "实查人数")
    private Integer inspectNum;

    @ApiModelProperty(value = "未查人数")
    private Integer unexploredNum;

    @ApiModelProperty(value = "男性人数")
    private Integer manNum;

    @ApiModelProperty(value = "女性人数")
    private Integer womenNum;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "上岗类型，详见：com.world.center.bean.enums.OnjobType")
    private Integer onjobType;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;
}
