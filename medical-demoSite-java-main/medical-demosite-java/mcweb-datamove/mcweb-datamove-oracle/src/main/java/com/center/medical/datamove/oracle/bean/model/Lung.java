package com.center.medical.datamove.oracle.bean.model;


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
 * KS肺功能(Lung)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:40
 */
@Data
@TableName("LUNG")
@ApiModel(value = "Lung", description = "KS肺功能实体类")
public class Lung extends Model<Lung> implements Serializable {
    private static final long serialVersionUID = -13512209464692994L;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "体检日期")
    private Date tjrq;

    @ApiModelProperty(value = "小结")
    private String xj;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用力肺活量(测定值)")
    private Double fvc;

    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1)）")
    private Double fev;

    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)")
    private Double fevFvc;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF %预测值）")
    private String percentageMmef;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF 预测值）")
    private String predictMmef;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF）")
    private String mmef;

    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）%预测值")
    private String percentageFeffa;

    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）预测值")
    private String predictFeffa;

    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）")
    private String feffa;

    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）%预测值")
    private String percentageFeffb;

    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）预测值")
    private String predictFeffb;

    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）")
    private String feffb;

    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）%预测值")
    private String percentageFeffc;

    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）预测值")
    private String predictFeffc;

    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）")
    private String feffc;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "用力肺活量（预测值）")
    private String predictFvc;

    @ApiModelProperty(value = "用力肺活量（%预测值）")
    private String percentageFvc;

    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1 预测值)")
    private String predictFev;

    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1% 预测值)")
    private String percentageFev;

    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)预测值")
    private String predictFevFvc;

    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)%预测值")
    private String percentageFevFvc;
}
