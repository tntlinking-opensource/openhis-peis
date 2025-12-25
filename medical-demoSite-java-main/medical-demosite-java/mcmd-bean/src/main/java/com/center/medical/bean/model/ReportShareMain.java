package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告分享主表(ReportShareMain)表实体类
 *
 * @author ay
 * @since 2023-09-19 16:19:53
 */
@Data
@TableName("md_report_share_main")
@ApiModel(value = "ReportShareMain", description = "报告分享主表实体类")
public class ReportShareMain extends Model<ReportShareMain> implements Serializable {
    private static final long serialVersionUID = -20862711176919557L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "分享链接")
    private String path;


    @ApiModelProperty(value = "链接名称")
    private String pathName;


    @ApiModelProperty(value = "提取码")
    private String extractedCode;


    @ApiModelProperty(value = "已选人数")
    private Integer num;


    @ApiModelProperty(value = "有效期 7,14,30,999")
    private Integer expirationDate;

    @ApiModelProperty(value = "过期时间")
    private Date expirationTime;


    @ApiModelProperty(value = "自动填充分享码 0否1是")
    private Integer autofill;


    @ApiModelProperty(value = "状态 0生效1过期")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "创建人姓名")
    private String createName;


    @ApiModelProperty(value = "创建人id")
    private String createId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;


    @ApiModelProperty(value = "修改人姓名")
    private String modifyName;


    @ApiModelProperty(value = "修改人id")
    private String modifyId;


    @ApiModelProperty(value = "访问次数")
    private Integer visits;


    @ApiModelProperty(value = "最近一次访问的ip")
    private String lastIp;


    @ApiModelProperty(value = "最近一次访问的时间")
    private Date lastTime;

}
