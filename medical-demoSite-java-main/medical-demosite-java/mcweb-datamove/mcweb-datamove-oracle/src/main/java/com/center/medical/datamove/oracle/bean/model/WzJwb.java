package com.center.medical.datamove.oracle.bean.model;


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
 * KS问诊——既往病(WzJwb)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:49
 */
@Data
@TableName("WZ_JWB")
@ApiModel(value = "WzJwb", description = "KS问诊——既往病实体类")
public class WzJwb extends Model<WzJwb> implements Serializable {
    private static final long serialVersionUID = -15663510916358418L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String occupationDiseast;
}
