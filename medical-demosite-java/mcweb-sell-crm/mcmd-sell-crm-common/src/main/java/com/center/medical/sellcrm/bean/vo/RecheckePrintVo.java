package com.center.medical.sellcrm.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 复查单打印分页返回数据
 */
@Data
public class RecheckePrintVo implements Serializable {
    private static final long serialVersionUID = 2084647618969458932L;


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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "复查PDF地址")
    private String reviewPdf;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "复查收费项目")
    private String itemsName;

    @ApiModelProperty(value = "客户单位名称")
    private String orgName;
}
