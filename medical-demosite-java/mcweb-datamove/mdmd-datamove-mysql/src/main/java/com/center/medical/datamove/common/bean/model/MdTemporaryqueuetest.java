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
 * temporaryqueuetest(MdTemporaryqueuetest)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:58
 */
@Data
@TableName("md_temporaryqueuetest")
@ApiModel(value = "MdTemporaryqueuetest", description = "temporaryqueuetest实体类")
public class MdTemporaryqueuetest extends Model<MdTemporaryqueuetest> implements Serializable {
    private static final long serialVersionUID = -66337622414370121L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "是否发送")
    private String transmitting;
}
