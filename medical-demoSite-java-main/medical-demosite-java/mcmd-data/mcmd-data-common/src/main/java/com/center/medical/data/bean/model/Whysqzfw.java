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
 * JC危害因素取值范围(Whysqzfw)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_whysqzfw")
@ApiModel(value = "Whysqzfw", description = "JC危害因素取值范围实体类")
public class Whysqzfw extends Model<Whysqzfw> implements Serializable {
    private static final long serialVersionUID = 220377502923996321L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素名字")
    private String harmName;

    @ApiModelProperty(value = "取值范围单位")
    private String unit;

    @ApiModelProperty(value = "检查项目名称")
    private String jcName;

    @ApiModelProperty(value = "检查项目ID")
    private String jcId;

    @ApiModelProperty(value = "危害因素ID")
    private String whId;

    @ApiModelProperty(value = "男取值范围上限")
    private Double scopeUpper;

    @ApiModelProperty(value = "男取值范围下限")
    private Double scoperFloor;

    @ApiModelProperty(value = "范围名称")
    private String scoperName;

    @ApiModelProperty(value = "范围代码")
    private String scoperCode;

    @ApiModelProperty(value = "LIS编号")
    private String lisId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "职业/综合")
    private Integer zyorjk;

    @ApiModelProperty(value = "危害因素输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "男女标识")
    private Integer sex;

    @ApiModelProperty(value = "女取值范围上限")
    private Double gscopeupper;

    @ApiModelProperty(value = "女取值范围下限")
    private Double gscoperfloor;

}
