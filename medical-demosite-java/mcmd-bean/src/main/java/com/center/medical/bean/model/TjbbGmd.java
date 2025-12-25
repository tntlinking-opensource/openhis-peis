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
 * KS骨密度体检报表(TjbbGmd)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_tjbb_gmd")
@ApiModel(value = "TjbbGmd", description = "KS骨密度体检报表实体类")
public class TjbbGmd extends Model<TjbbGmd> implements Serializable {
    private static final long serialVersionUID = 363721477117545628L;

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
