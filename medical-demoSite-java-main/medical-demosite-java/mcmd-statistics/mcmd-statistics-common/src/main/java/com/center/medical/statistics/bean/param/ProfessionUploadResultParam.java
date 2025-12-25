package com.center.medical.statistics.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 云平台上传结果分页查询参数
 * @author xhp
 * @since 2023-11-28 10:10
 */
@Data
public class ProfessionUploadResultParam implements Serializable {

    private static final long serialVersionUID = 2472743275901816100L;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "登记时间-开始时间 yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "登记时间-结束时间 yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "上传状态 0未上传 1上传成功 2上传失败")
    private Integer status;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "开单医生（姓名模糊查询）")
    private String doctorapply;

    @ApiModelProperty(value = "订单号")
    private String ddh;
}
