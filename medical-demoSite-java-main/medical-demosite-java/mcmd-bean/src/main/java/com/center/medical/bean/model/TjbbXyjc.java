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
 * KS血压检测(TjbbXyjc)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_tjbb_xyjc")
@ApiModel(value = "TjbbXyjc", description = "KS血压检测实体类")
public class TjbbXyjc extends Model<TjbbXyjc> implements Serializable {
    private static final long serialVersionUID = 235877942478891109L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "血压检测")
    private String xyjc;

    @ApiModelProperty(value = "收缩压参考范围")
    private String ckfwSsy;

    @ApiModelProperty(value = "舒张压参考范围")
    private String ckfwSzy;

    @ApiModelProperty(value = "收缩压低值")
    private Double valueMinSsy;

    @ApiModelProperty(value = "收缩压高值")
    private Double valueMaxSsy;

    @ApiModelProperty(value = "舒张压低值")
    private Double valueMinSzy;

    @ApiModelProperty(value = "舒张压高值")
    private Double valueMaxSzy;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;
}
