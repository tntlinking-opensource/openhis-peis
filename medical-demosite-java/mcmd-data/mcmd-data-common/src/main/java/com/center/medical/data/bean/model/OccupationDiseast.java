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
 * JC职业病名称(OccupationDiseast)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_occupation_diseast")
@ApiModel(value = "OccupationDiseast", description = "JC职业病名称实体类")
public class OccupationDiseast extends Model<OccupationDiseast> implements Serializable {
    private static final long serialVersionUID = 924867624057494187L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "职业病编码")
    private String occupationDiseastCode;

    @ApiModelProperty(value = "职业病名称")
    private String occupationDiseast;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "职业病分类ID")
    private String occupationDiseastClass;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @TableField(exist = false)
    @ApiModelProperty(value = "职业病分类名称")
    private String occupationDiseastClassName;
}
