package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.model.KdReser;
import com.center.medical.finance.bean.param.BSPageParam;
import com.center.medical.finance.bean.param.BankRefundParam;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 记账管理-银行汇款结算(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
public interface BankSettlementMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<BSPageVo> getList(PageParam<BSPageVo> page, @Param("param") BSPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param num 流水号
     * @return 详情信息
     */
    KdRemittance getInfoByNum(@Param("num") String num);

    /**
     * 导出统收
     *
     * @param param
     * @return
     */
    List<BankRefundVo> exportBankRefund(@Param("param") BankRefundParam param);

    /**
     * 获取一笔银行流水相关的详细记账结算
     *
     * @param page
     * @param transactionNumber
     * @return
     */
    IPage<KdReser> getReserBillingData(PageParam<KdReser> page, @Param("num") String transactionNumber);

    /**
     * 团检-统收 获取结算名字
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getNameNumber1(PageParam<NameNumberVo> page, @Param("key") String key);

    /**
     * 个检-记账 获取结算名字
     *
     * @param page
     * @return
     */
    IPage<NameNumberVo> getNameNumber2(PageParam<NameNumberVo> page);

    /**
     * 代收体检费-银行存款 获取结算名字
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getNameNumber3(PageParam<NameNumberVo> page, @Param("key") String key);

    /**
     * 团检-统收 获取结算名字（id号那列）
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getIdCustomer1(PageParam<NameNumberVo> page, @Param("key") String key, @Param("branchId") String branchId);

    /**
     * 个检-记账 获取结算名字（id号那列）
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getIdCustomer2(PageParam<NameNumberVo> page, @Param("key") String key);

    /**
     * 充卡-银行存款 获取结算名字（id号那列）
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getIdCustomer3(PageParam<NameNumberVo> page, @Param("key") String key);

    /**
     * 代收体检费-银行存款 获取结算名字（id号那列）
     *
     * @param page
     * @param key
     * @return
     */
    IPage<NameNumberVo> getIdCustomer4(PageParam<NameNumberVo> page, @Param("key") String key);

    /**
     * 获取销售经理1
     * @param page
     * @param id
     * @param key
     * @return
     */
    IPage<FeeChargerDataVo> getFeeChargerData1(PageParam<FeeChargerDataVo> page, @Param("id") String id, @Param("key") String key);

    /**
     * 代收体检费-银行存款
     * @param page
     * @param key
     * @return
     */
    IPage<FeeChargerDataVo> getFeeChargerData2(PageParam<FeeChargerDataVo> page,@Param("key") String key);

    /**
     * 查询汇总金额
     * @param param
     * @return
     */
    BankAmountVo summaryAmount(@Param("param") BSPageParam param);
}
