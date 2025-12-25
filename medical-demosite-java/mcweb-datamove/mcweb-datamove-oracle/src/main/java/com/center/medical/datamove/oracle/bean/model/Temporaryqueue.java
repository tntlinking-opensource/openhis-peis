package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Temporaryqueue)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:17
 */
@Data
@TableName("TEMPORARYQUEUE")
@ApiModel(value = "Temporaryqueue", description = "$tableInfo.comment实体类")
public class Temporaryqueue extends Model<Temporaryqueue> implements Serializable {
    private static final long serialVersionUID = 994828577483505341L;

    @TableId(value = "ID")
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer transmitting;
}
