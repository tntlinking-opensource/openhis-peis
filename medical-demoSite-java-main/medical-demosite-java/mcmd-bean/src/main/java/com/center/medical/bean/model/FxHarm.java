package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业团检分析-危害因素(FxHarm)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_harm")
@ApiModel(value = "FxHarm", description = "职业团检分析-危害因素实体类")
public class FxHarm extends Model<FxHarm> implements Serializable {
    private static final long serialVersionUID = 260544540195330209L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素id")
    private String harmId;

    @ApiModelProperty(value = "样本id")
    private String sampleId;

    @ApiModelProperty(value = "人数")
    private String personNum;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    public FxHarm(String sampleId, String harmId, String num, String harmName) {
        this.sampleId = sampleId;
        this.harmId = harmId;
        this.personNum = num;
        this.harmName = harmName;
    }
}
