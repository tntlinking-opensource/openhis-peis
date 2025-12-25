package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.dto.PeispatientchargeDto;
import com.center.medical.bean.model.Peispatientcharge;
import com.center.medical.bean.param.*;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 体检者缴费表(Peispatientcharge)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
public interface PeispatientchargeService extends IService<Peispatientcharge> {

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param Peispatientcharge查询参数
     * @return 分页数据
     */
    IPage<Peispatientcharge> getList(PageParam<Peispatientcharge> page, Peispatientcharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Peispatientcharge getInfoById(String id);

    /**
     * 团检客户没有增加项目自动保存收费信息
     *
     * @param chargeDto
     * @return
     */
    Boolean autoSaveOrUpdate(PeispatientchargeDto chargeDto);

    /**
     * 保存收费信息
     *
     * @param patientCode
     * @param griddata
     * @param removeIds
     * @param noticeJyk
     * @param check
     * @return
     */
    Boolean saveOrUpdates(String patientCode, List<PeispatientchargeDto> griddata, String removeIds, String noticeJyk, boolean check);

    /**
     * 获取体检者收费信息
     *
     * @param patientCode
     * @return
     */
    List<Peispatientcharge> getChargeData(String patientCode);

    /**
     * 登记后收费
     *
     * @param param
     * @return
     */
    Boolean receiveRefund(ChargeParam param);

    /**
     * 支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    PayResultDto pay(PayParam param);

    /**
     * 退款：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 退款参数
     * @return
     */
    Boolean refund(RefundParam param);

    /**
     * 复查额度消费
     * @param param
     * @return
     */
    PayResultDto chargeRecheck(ChargeRecheckParam param);

    /**
     * 保存收费信息
     *
     * @param param
     * @return
     */
    Boolean saveOrUpdateCharge(ChargeParam param);

    /**
     * 前台-登记-退费-反收费
     *
     * @param patientCode 体检号
     * @param version     版本号
     * @return
     */
    R reCharge(String patientCode, Long version);

    /**
     * 费用预收
     *
     * @param param
     * @return
     */
    Boolean advanceRefund(AdvanceRefundParam param);

    /**
     * 预收支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    PayResultDto advancePay(PayParam param);

    /**
     * 手动通联退款(测试使用)
     * @param param
     * @return
     */
    Boolean tongLianRefund(TongLianRefundParam param);

    /**
     * 更改收费方式
     * @param param
     * @return
     */
    Boolean changePaymentMethod(ChangePaymentMethodParam param);

    /**
     * 修改收费方式(管理员)
     * @param param
     * @return
     */
    Boolean changePaymentMethodByAdmin(ChangePaymentMethodParam param);
}

