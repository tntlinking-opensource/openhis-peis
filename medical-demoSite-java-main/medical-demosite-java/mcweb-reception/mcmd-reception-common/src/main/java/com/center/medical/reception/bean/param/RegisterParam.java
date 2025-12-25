package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-01 16:40
 * @description: 登记列表查询参数
 */
@Data
public class RegisterParam extends BaseParam {

    private static final long serialVersionUID = 781438241996835689L;

    @ApiModelProperty(value = "autoFill")
    private Boolean autoFill;

    @ApiModelProperty(value = "简码")
    private String jm;

    @ApiModelProperty(value = "体检号从")
    private String ptcodeFrom;

    @ApiModelProperty(value = "体检号到")
    private String ptcodeTo;

    @ApiModelProperty(value = "拼音码")
    private String inputCode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "登记人ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检团体ID")
    private String khdwmcid;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "打印状态：0.未打印 1.已打印")
    private Integer pCount;

    @ApiModelProperty(value = "状态：全部0 条码1 导引单2")
    private Integer printType;

    @ApiModelProperty(value = "禁检：0.未禁检 1.已禁检")
    private Integer paused;

    @ApiModelProperty(value = "订单结束状态：0.未结束 1.已结束")
    private Integer orderFinished;

    @ApiModelProperty(value = "体检者类型：调用用户等级列表接口，使用会员等级ID（levelId）")
    private Integer idPatientclass;

    @ApiModelProperty(value = "开单医师ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer useCodeHiden;

    @ApiModelProperty(value = "是否登记：0.未登记 1.已登记")
    private Integer fRegistered;
    
    @ApiModelProperty(value = "售卡人ID")
    private String sellId;

    @ApiModelProperty(value = "确认来检：0或null.否 1.是")
    private Integer idPatientclass3;

    @ApiModelProperty(value = "体检时间(这个体检时间必须时分秒对上才能查出来)")
    private Date dateTime;

    @ApiModelProperty(value = "是否预收 0.否 1.是")
    private Integer sfys;


    @ApiModelProperty(value = "用户编号（用于数据隔离）")
    private String userNo;

    @ApiModelProperty(value = "用户姓名（用于数据隔离，查询该用户是否是别的订单的开单助理）")
    private String userName;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;
}
