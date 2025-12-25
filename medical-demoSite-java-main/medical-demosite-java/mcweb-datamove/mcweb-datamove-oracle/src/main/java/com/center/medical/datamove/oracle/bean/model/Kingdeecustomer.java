package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (Kingdeecustomer)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:24
 */
@Data
@TableName("KINGDEECUSTOMER")
@ApiModel(value = "Kingdeecustomer", description = "$tableInfo.comment实体类")
public class Kingdeecustomer extends Model<Kingdeecustomer> implements Serializable {
    private static final long serialVersionUID = 403211579766107162L;

    @ApiModelProperty(value = "${column.comment}")
    private String accountName;

    @ApiModelProperty(value = "${column.comment}")
    private String accountNo;

    @ApiModelProperty(value = "${column.comment}")
    private String useStatusId;

    @ApiModelProperty(value = "${column.comment}")
    private Date ctDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date ltDate;

    @ApiModelProperty(value = "${column.comment}")
    private String centerorgname;
}
