package com.center.medical.reception.bean.vo;

import com.center.medical.bean.enums.ExamType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-11-23 10:11
 * @description: 复查打印数据
 */
@Data
public class ReviewPrintVo implements Serializable {
    private static final long serialVersionUID = -6613228062120114119L;

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

    @ApiModelProperty(value = "注意事项")
    private String notice; //Review.noticeOfProceedingText

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String type; //Peispatient.idExamtype

    @ApiModelProperty(value = "分中心地址")
    private String fzxAddress; //SysBranch.address

    @ApiModelProperty(value = "复查项目列表")
    private List<ReviewItemVo> items;

    @ApiModelProperty(value = "客户单位名称")
    private String orgName;

    @ApiModelProperty(value = "成功标识 ")
    private Boolean success;

    @ApiModelProperty(value = "打印操作结果")
    private String msg;

}
