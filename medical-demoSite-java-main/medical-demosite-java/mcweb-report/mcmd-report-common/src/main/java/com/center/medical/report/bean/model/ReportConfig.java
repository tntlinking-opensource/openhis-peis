package com.center.medical.report.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告配置(ReportConfig)表实体类
 *
 * @author ay
 * @since 2023-07-25 10:00:31
 */
@Data
@TableName("md_report_config")
@ApiModel(value = "ReportConfig", description = "报告配置实体类")
public class ReportConfig extends Model<ReportConfig> implements Serializable {
    private static final long serialVersionUID = -21666519148109311L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "状态：0.正常 1.删除")
    private String status;

    @ApiModelProperty(value = "是否默认，1是0否，最多只能有一个")
    private Integer isDefault;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String memo;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名字")
    private String branchName;
}
