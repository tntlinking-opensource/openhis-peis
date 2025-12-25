package com.center.medical.pacs.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * pacs体检者列表分页查询参数
 *
 * @author xhp
 * @since 2023-03-16 8:47
 */
@Data
public class PatientListParam implements Serializable {
    private static final long serialVersionUID = 6083972242687967483L;
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "0无图 1有图")
    private Integer jcstatus;

    @ApiModelProperty(value = "部门编号", required = true)
    @NotBlank
    private String deptNo;

    @ApiModelProperty(value = "0未检 1已检")
    private Integer status;

    @ApiModelProperty(value = "体检者类型名称  1普通会员 2VIP 3VVIP")
    private String idPatientClass;

    @ApiModelProperty(value = "是否补全体检号，true是 false否")
    private String autoFill;

}
