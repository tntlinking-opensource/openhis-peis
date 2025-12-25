package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 平安软件-排检(PaPeissortexam)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pa_peissortexam")
@ApiModel(value = "PaPeissortexam", description = "平安软件-排检实体类")
public class PaPeissortexam extends Model<PaPeissortexam> implements Serializable {
    private static final long serialVersionUID = 425242152337730984L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "普检区可预约人数")
    private Double areaId;

    @ApiModelProperty(value = "VIP可预约人数")
    private Double sortNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date sortDate;

    @ApiModelProperty(value = "是否开检：0或null.否 1.是")
    private Double isOpen;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;


}
