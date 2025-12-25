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
 * KS血脂检测体检报表(TjbbXzjc)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_tjbb_xzjc")
@ApiModel(value = "TjbbXzjc", description = "KS血脂检测体检报表实体类")
public class TjbbXzjc extends Model<TjbbXzjc> implements Serializable {
    private static final long serialVersionUID = -82863331473879425L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "分类")
    private String fl;

    @ApiModelProperty(value = "血脂检测项目")
    private String xzjcmc;

    @ApiModelProperty(value = "参考范围")
    private String ckfw;

    @ApiModelProperty(value = "低值")
    private Double valueMin;

    @ApiModelProperty(value = "高值")
    private Double valueMax;

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
