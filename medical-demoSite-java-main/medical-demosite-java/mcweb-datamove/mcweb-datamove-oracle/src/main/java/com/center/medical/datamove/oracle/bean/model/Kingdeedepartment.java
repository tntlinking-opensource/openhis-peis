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
 * (Kingdeedepartment)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:26
 */
@Data
@TableName("KINGDEEDEPARTMENT")
@ApiModel(value = "Kingdeedepartment", description = "$tableInfo.comment实体类")
public class Kingdeedepartment extends Model<Kingdeedepartment> implements Serializable {
    private static final long serialVersionUID = 892251764162396744L;

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
