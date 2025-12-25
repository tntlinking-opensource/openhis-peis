package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业性问诊保存表格数据
 */
@Data
public class ComGridDataDto implements Serializable {
    private static final long serialVersionUID = 4696207453709673802L;

    @ApiModelProperty(value = "毒害种类和名称")
    private String occupationHarm;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "更新时间")
    private Date modifyDate;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "工种")
    private String typeOfWork;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "截止年月")
    private Date stopDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始年月")
    private Date startDate;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "_id")
    private String _id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "状态 removed删除，modified修改，added添加")
    private String state;

    @ApiModelProperty(value = "部门")
    private String branch;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "工作单位ID")
    private String deptId;

    @ApiModelProperty(value = "有无防护,0无，1有")
    private String occupationDefend;

}
