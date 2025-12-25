package com.center.medical.datamove.common.bean.model;


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
 * KS血糖检测体检报表(MdTjbbXtjc)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:03
 */
@Data
@TableName("md_tjbb_xtjc")
@ApiModel(value = "MdTjbbXtjc", description = "KS血糖检测体检报表实体类")
public class MdTjbbXtjc extends Model<MdTjbbXtjc> implements Serializable {
    private static final long serialVersionUID = 599723600584268968L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "血糖检测")
    private String xtjc;

    @ApiModelProperty(value = "参考范围描述")
    private String ckfw;

    @ApiModelProperty(value = "低值")
    private Double valueMin;

    @ApiModelProperty(value = "高值")
    private Double valueMax;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;
}
