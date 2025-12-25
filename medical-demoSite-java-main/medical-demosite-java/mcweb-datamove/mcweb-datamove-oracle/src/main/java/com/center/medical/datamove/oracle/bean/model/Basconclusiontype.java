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
 * (Basconclusiontype)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:24
 */
@Data
@TableName("BASCONCLUSIONTYPE")
@ApiModel(value = "Basconclusiontype", description = "$tableInfo.comment实体类")
public class Basconclusiontype extends Model<Basconclusiontype> implements Serializable {
    private static final long serialVersionUID = -94650827343699582L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "结论词分类")
    private String keyconclusiontype;

    @ApiModelProperty(value = "分类名称")
    private String conclusiontypeName;

    @ApiModelProperty(value = "分类英文名称")
    private String conclusiontypeNameeng;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusiontypeCode;

    @ApiModelProperty(value = "分类输入码")
    private String inputCode;

    @ApiModelProperty(value = "分类展现顺序（用户总检汇总时顺序控制）")
    private String disporder;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;
}
