package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.model.KdReser;
import com.center.medical.finance.bean.param.BSPageParam;
import com.center.medical.finance.bean.param.BankRefundParam;
import com.center.medical.finance.bean.param.FeeChargerDataParam;
import com.center.medical.finance.bean.param.UpBankRefundParam;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.bean.vo.*;

import java.util.List;

/**
 * 记账管理-银行汇款结算(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
public interface BankSettlementService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BSPageVo> getList(PageParam<BSPageVo> page, BSPageParam param);

    /**
     * 根据流水号获取记录详情
     *
     * @param num 流水号
     * @return 详情信息
     */
    KdRemittance getInfoByNum(String num);

    /**
     * 导出统收
     *
     * @param param
     * @return
     */
    List<BankRefundVo> exportBankRefund(BankRefundParam param);

    /**
     * 获取一笔银行流水相关的详细记账结算
     *
     * @param page
     * @param transactionNumber
     * @return
     */
    IPage<KdReser> getReserBillingData(PageParam<KdReser> page, String transactionNumber);

    /**
     * 获取结算名字
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getNameNumber(PageParam<NameNumberVo> page, String way, String key);

    /**
     * 获取结算名字（id号那列）
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getIdCustomer(PageParam<NameNumberVo> page, String key,String way);

    /**
     * 银行汇款结算-保存
     *
     * @param param
     * @return
     */
    Boolean updateBankRefund(UpBankRefundParam param);

    /**
     * 审核
     *
     * @param rowsId
     * @return
     */
    String approve(List<String> rowsId);

    /**
     * 反审核
     *
     * @param rowsId
     * @return
     */
    String unapprove(List<String> rowsId);

    /**
     * 获取销售经理
     * @param param
     * @return
     */
    IPage<FeeChargerDataVo> getFeeChargerData(PageParam<FeeChargerDataVo> page, FeeChargerDataParam param);

    /**
     * 查询汇总金额
     * @param param
     * @return
     */
    BankAmountVo summaryAmount(BSPageParam param);
}

