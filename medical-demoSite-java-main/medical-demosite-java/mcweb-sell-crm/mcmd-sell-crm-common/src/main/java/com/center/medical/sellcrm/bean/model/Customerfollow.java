package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.CustomerFollowStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户跟踪表(Customerfollow)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:47
 */
@Data
@TableName("crm_customerfollow")
@ApiModel(value = "Customerfollow", description = "客户跟踪表实体类")
public class Customerfollow extends Model<Customerfollow> implements Serializable {
    private static final long serialVersionUID = -57957458132474835L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "主题")
    private String zt;

    @ApiModelProperty(value = "客户负责人")
    private String khfzr;

    @ApiModelProperty(value = "便于维护是哪个销售经理填写的记录")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;


    /**
     * @see CustomerFollowStatus
     */
    @ApiModelProperty(value = "客户跟踪阶段 0.需求交流 1.方案谈判 2.价格谈判 3.体检确认 4.合同签订")
    private Integer gjjd;

    @ApiModelProperty(value = "跟进日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gjrq;

    @ApiModelProperty(value = "跟进内容")
    private String gjnr;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jsrq;

    @TableField(exist = false)
    @ApiModelProperty(value = "结束阶段")
    private Boolean jdjs;

    @TableField(exist = false)
    @ApiModelProperty(value = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ksrq;


    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;


    @TableField(exist = false)
    @ApiModelProperty(value = "进行天数")
    private Double jxsj;
}
