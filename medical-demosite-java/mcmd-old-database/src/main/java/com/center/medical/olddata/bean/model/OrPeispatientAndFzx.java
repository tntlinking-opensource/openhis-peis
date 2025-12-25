package com.center.medical.olddata.bean.model;


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
 * (PeispatientAndFzx)表实体类
 *
 * @author ay
 * @since 2024-04-17 10:37:32
 */
@Data
@TableName("PEISPATIENT_AND_FZX")
@ApiModel(value = "PeispatientAndFzx", description = "$tableInfo.comment实体类")
public class OrPeispatientAndFzx extends Model<OrPeispatientAndFzx> implements Serializable {
    private static final long serialVersionUID = 402111740340867663L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;


    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;


    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;


    @ApiModelProperty(value = "${column.comment}")
    private String patientId;


    @ApiModelProperty(value = "${column.comment}")
    private String fzxId;


    @ApiModelProperty(value = "${column.comment}")
    private Integer xzzt;

}
