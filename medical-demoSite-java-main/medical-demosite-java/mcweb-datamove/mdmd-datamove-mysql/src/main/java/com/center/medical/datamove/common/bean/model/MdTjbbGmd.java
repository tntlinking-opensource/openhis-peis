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
 * KS骨密度体检报表(MdTjbbGmd)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:01
 */
@Data
@TableName("md_tjbb_gmd")
@ApiModel(value = "MdTjbbGmd", description = "KS骨密度体检报表实体类")
public class MdTjbbGmd extends Model<MdTjbbGmd> implements Serializable {
    private static final long serialVersionUID = -29629559046111880L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "骨密度描述")
    private String gmd;

    @ApiModelProperty(value = "参考范围描述")
    private String ckfw;

    @ApiModelProperty(value = "低值")
    private Double valueMin;

    @ApiModelProperty(value = "高值")
    private Double valueMax;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}
