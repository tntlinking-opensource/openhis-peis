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
 * KS问诊——复查随访(MdWzCallback)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:22
 */
@Data
@TableName("md_wz_callback")
@ApiModel(value = "MdWzCallback", description = "KS问诊——复查随访实体类")
public class MdWzCallback extends Model<MdWzCallback> implements Serializable {
    private static final long serialVersionUID = 892997245520536734L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "复查时间起")
    private Date dateFrom;

    @ApiModelProperty(value = "复查时间止")
    private Date dateTo;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "复查项目")
    private String itemName;

    @ApiModelProperty(value = "复查结果")
    private String resultText;

    @ApiModelProperty(value = "总结代码")
    private String summaryCode;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "复查内容")
    private String callbackOrderText;

    @ApiModelProperty(value = "复查状态")
    private String callbackStation;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "复查类别")
    private String callbackClass;

    @ApiModelProperty(value = "注意事项")
    private String noticeOfProceedingText;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}
