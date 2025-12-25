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
 * 平安软件-排检(MdPaPeissortexam)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
@Data
@TableName("md_pa_peissortexam")
@ApiModel(value = "MdPaPeissortexam", description = "平安软件-排检实体类")
public class MdPaPeissortexam extends Model<MdPaPeissortexam> implements Serializable {
    private static final long serialVersionUID = 272646082184367314L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "普检区可预约人数")
    private Object areaId;

    @ApiModelProperty(value = "VIP可预约人数")
    private Object sortNum;

    @ApiModelProperty(value = "日期")
    private Date sortDate;

    @ApiModelProperty(value = "是否开检：0或null.否 1.是")
    private String isOpen;
}
