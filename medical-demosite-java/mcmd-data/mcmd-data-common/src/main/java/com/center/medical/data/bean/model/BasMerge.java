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
 * 合并结伦词(BasMerge)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_bas_merge")
@ApiModel(value = "BasMerge", description = "合并结伦词实体类")
public class BasMerge extends Model<BasMerge> implements Serializable {
    private static final long serialVersionUID = 979884494610866030L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "结论名称")
    private String mergeName;

    @ApiModelProperty(value = "总检建议")
    private String suggestion;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "团检建议")
    private String tjjy;

    @ApiModelProperty(value = "创建人")
    private String creater;


}
