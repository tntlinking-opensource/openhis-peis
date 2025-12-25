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
 * 总检结论词类型(MdBasconclusiontype)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
@Data
@TableName("md_basconclusiontype")
@ApiModel(value = "MdBasconclusiontype", description = "总检结论词类型实体类")
public class MdBasconclusiontype extends Model<MdBasconclusiontype> implements Serializable {
    private static final long serialVersionUID = 537634464573345858L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "记录ID")
    private String id;

    @ApiModelProperty(value = "结论词分类")
    private String keyconclusiontype;

    @ApiModelProperty(value = "分类名称")
    private String conclusiontypeName;

    @ApiModelProperty(value = "分类英文名称")
    private String conclusiontypeNameeng;

    @ApiModelProperty(value = "分类码")
    private String conclusiontypeCode;

    @ApiModelProperty(value = "分类输入码")
    private String inputCode;

    @ApiModelProperty(value = "分类展现顺序（用户总检汇总时顺序控制）")
    private String disporder;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;
}
