package com.center.medical.machine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.model.Report;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.machine.bean.param.ReportPrintPaymentParam;
import com.center.medical.machine.bean.param.SendVerificationCodeParam;
import com.center.medical.machine.bean.param.SuccessCallbackParam;
import com.center.medical.machine.bean.param.VerificationCodeLoginParam;
import com.center.medical.machine.bean.vo.ReportPrintListVo;


import java.util.List;
import java.util.Map;

/**
 * BG报告主表(Report)表服务接口
 *
 * @author ay
 * @since 2023-05-30 10:20:50
 */
public interface ReportPrintService extends IService<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Report> getList(PageParam<Report> page, Report param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(String id);

    /**
     * 打印指南
     * @param value
     * @return
     */
    Map<String, Object> extracted(int value);

    /**
     * 发送验证码
     * @param param
     */
    Boolean sendVerificationCode(SendVerificationCodeParam param);

    /**
     * 验证码登录
     * @param param
     * @return
     */
    String verificationCodeLogin(VerificationCodeLoginParam param);

    /**
     * 报告打印列表
     * @param userToken
     * @return
     */
    List<ReportPrintListVo> reportPrintList(String userToken);

    /**
     * 加密身份证号
     * @param idcardno
     * @return
     */
    String patientComplete(String idcardno);

    /**
     * 报告打印支付
     * @param param
     * @return
     */
    PayResultDto reportPrintPayment(ReportPrintPaymentParam param);

    /**
     * 支付成功回调方法
     * @param param
     * @return
     */
    String successCallback(SuccessCallbackParam param);
}

