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
 * 职业危害因素分类(ZyHarmClass)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:19
 */
@Data
@TableName("md_zy_harm_class")
@ApiModel(value = "ZyHarmClass", description = "职业危害因素分类实体类")
public class ZyHarmClass extends Model<ZyHarmClass> implements Serializable {
    private static final long serialVersionUID = 132359301204126611L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "危害因_id号")
    private String harId;

    @ApiModelProperty(value = "危害种类代码")
    private String harmClassCode;

    @ApiModelProperty(value = "危害因素种类")
    private String harmClass;

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

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否删除 0：不删除，1：删除")
    private Integer isDelete;
}
