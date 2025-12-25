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
 * KS骨密度体检报表(TjbbGmd)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:25
 */
@Data
@TableName("TJBB_GMD")
@ApiModel(value = "TjbbGmd", description = "KS骨密度体检报表实体类")
public class TjbbGmd extends Model<TjbbGmd> implements Serializable {
    private static final long serialVersionUID = 288641063807094442L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "序号")
    private Double xh;

    @ApiModelProperty(value = "骨密度描述")
    private String gmd;

    @ApiModelProperty(value = "参考范围描述")
    private String ckfw;

    @ApiModelProperty(value = "低值")
    private String valueMin;

    @ApiModelProperty(value = "高值")
    private String valueMax;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}
