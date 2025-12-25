package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * temporaryqueuetest(Temporaryqueuetest)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_temporaryqueuetest")
@ApiModel(value = "Temporaryqueuetest", description = "temporaryqueuetest实体类")
public class Temporaryqueuetest extends Model<Temporaryqueuetest> implements Serializable {
    private static final long serialVersionUID = 474354313188236921L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "是否发送")
    private Double transmitting;
}
