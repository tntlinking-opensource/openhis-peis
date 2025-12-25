package com.center.medical.bean.model;

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
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(FxReviewInfo)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_review_info")
@ApiModel(value = "FxReviewInfo", description = "职业健康检查职业病危害效应相关指标异常需要复查人员实体类")
public class FxReviewInfo extends Model<FxReviewInfo> implements Serializable {
    private static final long serialVersionUID = -47025261867029455L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素ID")
    private String harmId;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "职业禁忌症名称")
    private String diagnosis;

    @ApiModelProperty(value = "人数")
    private Integer num;

    @ApiModelProperty(value = "建议")
    private String summaryText;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    public FxReviewInfo(String sampleId, String harmId, String harmName,
                        String diagnosis, Integer num, String summaryText) {
        super();
        this.sampleId = sampleId;
        this.harmId = harmId;
        this.harmName = harmName;
        this.diagnosis = diagnosis;
        this.num = num;
        this.summaryText = summaryText;
    }


    public FxReviewInfo() {
    }
}
