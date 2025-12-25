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
 * 发票类型(MdReceiptType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Data
@TableName("md_receipt_type")
@ApiModel(value = "MdReceiptType", description = "发票类型实体类")
public class MdReceiptType extends Model<MdReceiptType> implements Serializable {
    private static final long serialVersionUID = -25403384076336497L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "发票团体名称")
    private String receiptTypeName;

    @ApiModelProperty(value = "发票类型代码")
    private String receiptTypeCode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
