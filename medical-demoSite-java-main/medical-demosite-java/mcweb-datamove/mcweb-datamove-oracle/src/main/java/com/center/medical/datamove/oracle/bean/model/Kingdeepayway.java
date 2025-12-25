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
 * (Kingdeepayway)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:28
 */
@Data
@TableName("KINGDEEPAYWAY")
@ApiModel(value = "Kingdeepayway", description = "$tableInfo.comment实体类")
public class Kingdeepayway extends Model<Kingdeepayway> implements Serializable {
    private static final long serialVersionUID = 872884696468519862L;

    @ApiModelProperty(value = "${column.comment}")
    private String accountNo;

    @ApiModelProperty(value = "${column.comment}")
    private String accountName;

    @ApiModelProperty(value = "${column.comment}")
    private String useStatusId;

    @ApiModelProperty(value = "${column.comment}")
    private Date ctDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date ltDate;
}
