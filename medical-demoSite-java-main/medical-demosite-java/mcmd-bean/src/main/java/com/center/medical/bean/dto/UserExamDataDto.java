package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞船长
 * @date: 2024/12/10 06:09
 * @description: 体检者体检数据
 */
@Data
public class UserExamDataDto implements Serializable {

    private static final long serialVersionUID = 2933719374502063105L;

    /**
     * 本次请求的唯一标识，调用回调接口推送数据时会携带该参数，用于标记是本次请求的结果数据
     */
    @ApiModelProperty(value = "请求标识")
    private String flag;

    /**
     * 体检人
     */
    @ApiModelProperty(value = "体检人")
    private String patientname;

    /**
     * 体检号
     */
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 家庭住址
     */
    @ApiModelProperty(value = "家庭住址")
    private String address;

    /**
     * 性别: 0.男 1.女
     */
    @ApiModelProperty(value = "性别: 0.男 1.女")
    private Integer sex;

    /**
     * 分中心名称
     */
    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "企业客户ID")
    private String customerId;

    /**
     * 企业客户名称
     */
    @ApiModelProperty(value = "企业客户名称")
    private String customerName;

    /**
     * 所在部门
     */
    @ApiModelProperty(value = "所在部门")
    private String orgDepart;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderId;

    /**
     * 体检类型：0.健康体检 1.职业体检 2.综合 3.复查
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer examType;

    /**
     * 状态：0未来检 1已来检 2体检完成
     */
    @ApiModelProperty(value = "状态：0未来检 1已来检 2体检完成")
    private Integer status;

    /**
     * 登记时间
     */
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    /**
     * 检查项目列表
     */
    @ApiModelProperty(value = "检查项目列表")
    private List<ExamItemDataDto> examtemList;

    /**
     * 健康总检结果
     */
    @ApiModelProperty(value = "健康总检结果")
    private HealthResultDto healthResult;

    /**
     * 职业总检结果
     */
    @ApiModelProperty(value = "职业总检结果")
    private OccupResultDto occupResult;
}
