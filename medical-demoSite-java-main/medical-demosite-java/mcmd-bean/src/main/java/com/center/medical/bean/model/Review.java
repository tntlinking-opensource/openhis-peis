package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * ZJ复查表(Review)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:26
 */
@Data
@TableName("md_review")
@ApiModel(value = "Review", description = "ZJ复查表实体类")
public class Review extends Model<Review> implements Serializable {
    private static final long serialVersionUID = 968167946865511762L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "复查时间起")
    private Date dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "复查时间止")
    private Date dateTo;

    @ApiModelProperty(value = "复查状态")
    private Integer callbackStation;

    @ApiModelProperty(value = "操作员ID")
    private String userId;

    @ApiModelProperty(value = "注意事项")
    private String noticeOfProceedingText;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "复查PDF地址")
    private String reviewPdf;

    @TableField(exist = false)
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @TableField(exist = false)
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @TableField(exist = false)
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @TableField(exist = false)
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @TableField(exist = false)
    @ApiModelProperty(value = "复查收费项目")
    private String itemsName;

    @TableField(exist = false)
    @ApiModelProperty(value = "客户单位名称")
    private String orgName;
}
