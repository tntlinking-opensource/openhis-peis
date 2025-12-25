package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 总检结论词类型(Basconclusiontype)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_basconclusiontype")
@ApiModel(value = "Basconclusiontype", description = "总检结论词类型实体类")
public class Basconclusiontype extends Model<Basconclusiontype> implements Serializable {
    private static final long serialVersionUID = -56868074701784241L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
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

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "分类展现顺序（用户总检汇总时顺序控制）")
    private String disporder;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;
}
