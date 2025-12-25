package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.AccountNameDto;
import com.center.medical.finance.bean.dto.BRGriddataDto;
import com.center.medical.finance.bean.model.KdPayway;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.model.KdReser;
import com.center.medical.finance.bean.param.BSPageParam;
import com.center.medical.finance.bean.param.BankRefundParam;
import com.center.medical.finance.bean.param.FeeChargerDataParam;
import com.center.medical.finance.bean.param.UpBankRefundParam;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.dao.BankSettlementMapper;
import com.center.medical.finance.dao.KdPaywayMapper;
import com.center.medical.finance.dao.KdReserMapper;
import com.center.medical.finance.service.BankSettlementService;
import com.center.medical.finance.service.KdPaywayService;
import com.center.medical.finance.service.KdRemittanceService;
import com.center.medical.finance.service.KdReserService;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 记账管理-银行汇款结算(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
@Slf4j
@Service("bankSettlementService")
@RequiredArgsConstructor
public class BankSettlementServiceImpl extends ServiceImpl<BankSettlementMapper, Peispatient> implements BankSettlementService {

    private final BankSettlementMapper bankSettlementMapper;
    private final KdPaywayMapper kdPaywayMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final MapperFacade mapperFacade;
    private final KdReserMapper kdReserMapper;
    private final KdReserService kdReserService;
    private final KdPaywayService kdPaywayService;
    private final ISysBranchService sysBranchService;
    private final KdRemittanceService kdrRemittanceService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BSPageVo> getList(PageParam<BSPageVo> page, BSPageParam param) {
        return bankSettlementMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param num 流水号
     * @return 详情信息
     */
    @Override
    public KdRemittance getInfoByNum(String num) {
        return bankSettlementMapper.getInfoByNum(num);
    }

    /**
     * 导出统收
     *
     * @param param
     * @return
     */
    @Override
    public List<BankRefundVo> exportBankRefund(BankRefundParam param) {

        String task = param.getTask();
        if (ObjectUtils.isNotEmpty(param.getTask())) {
            if (task.equals("TongShou")) {
                param.setReserWay("团检-统收");
            } else if (task.equals("ChongKa")) {
                param.setReserWay("充卡-银行存款");
            } else if (task.equals("DaiShou")) {
                param.setReserWay("代收体检费-银行存款");
            } else if (task.equals("GeJian")) {
                param.setReserWay("个检-记账");
            }
        }
        return bankSettlementMapper.exportBankRefund(param);
    }


    /**
     * 获取一笔银行流水相关的详细记账结算
     *
     * @param page
     * @param transactionNumber
     * @return
     */
    @Override
    public IPage<KdReser> getReserBillingData(PageParam<KdReser> page, String transactionNumber) {
        return bankSettlementMapper.getReserBillingData(page, transactionNumber);
    }

    /**
     * 获取结算名字
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<NameNumberVo> getNameNumber(PageParam<NameNumberVo> page, String way, String key) {

        if (ObjectUtils.isNotEmpty(key)){
            //去除空格
            key = key.trim();
        }
        if (ObjectUtils.isNotEmpty(way)){
            //去除空格
            way = way.trim();
        }
        //查询支付方式
        QueryWrapper<KdPayway> queryWrapper = new QueryWrapper<KdPayway>()
                .eq("use_status_id", 1).groupBy("account_no").orderByDesc("account_no");
        if (StringUtils.isNotEmpty(way)) {
            queryWrapper.eq("account_no", way);
        }
        List<KdPayway> kdPayways = kdPaywayMapper.selectList(queryWrapper);

        IPage<NameNumberVo> iPage = new PageParam<>();
        //查询
        if (CollectionUtils.isNotEmpty(kdPayways)) {
            String accountName = kdPayways.get(0).getAccountName();
            if (accountName.equals("团检-统收")) {
                iPage = bankSettlementMapper.getNameNumber1(page, key);
            } else if (accountName.equals("个检-记账")) {
                iPage = bankSettlementMapper.getNameNumber2(page);
            } else if (accountName.equals("充卡-银行存款")) {
                List<NameNumberVo> records = new ArrayList<>();
                NameNumberVo vo = new NameNumberVo();
                vo.setName("体检卡");
                vo.setId("");
                records.add(vo);
                iPage.setRecords(records);
            } else if (accountName.equals("代收体检费-银行存款")) {
                iPage = bankSettlementMapper.getNameNumber3(page, key);
            }
        }
        return iPage;
    }

    /**
     * 获取结算名字（id号那列）
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<NameNumberVo> getIdCustomer(PageParam<NameNumberVo> page, String key,String way) {
        if (ObjectUtils.isNotEmpty(key)){
            //去除空格
            key = key.trim();
        }
        if (ObjectUtils.isNotEmpty(way)){
            //去除空格
            way = way.trim();
        }
        //查询支付方式
        QueryWrapper<KdPayway> queryWrapper = new QueryWrapper<KdPayway>()
                .eq("use_status_id", 1).groupBy("account_no").orderByDesc("account_no");
        if (StringUtils.isNotEmpty(way)) {
            queryWrapper.eq("account_no", way);
        }
        List<KdPayway> kdPayways = kdPaywayMapper.selectList(queryWrapper);

        IPage<NameNumberVo> iPage = new PageParam<>();
        //查询
        if (CollectionUtils.isNotEmpty(kdPayways)) {
            String accountName = kdPayways.get(0).getAccountName();
            if (accountName.equals("团检-统收")) {
                iPage = bankSettlementMapper.getIdCustomer1(page, key ,SecurityUtils.getCId());
            } else if (accountName.equals("个检-记账")) {
                iPage = bankSettlementMapper.getIdCustomer2(page, key);
            } else if (accountName.equals("充卡-银行存款")) {
                iPage = bankSettlementMapper.getIdCustomer3(page, key);
            } else if (accountName.equals("代收体检费-银行存款")) {
                iPage = bankSettlementMapper.getIdCustomer4(page, key);
            }
        }
        return iPage;
    }

    /**
     * 银行汇款结算-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateBankRefund(UpBankRefundParam param) {
        // 获取银行流水号
        KdRemittance kingdeeRemittance = mapperFacade.map(param.getFormdata(), KdRemittance.class);
        //对方账户编号
        String chargesId = kingdeeRemittance.getTransactionnumber();
        Boolean caiwu = SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE);
        // 结算金额
        List<BRGriddataDto> featureData = param.getGriddata();
        List<KdReser> list = new ArrayList<KdReser>();
        for (int i = 0, l = featureData.size(); i < l; i++) {
            BRGriddataDto map = featureData.get(i);
            //每笔银行汇款结算详情
            KdReser kingdeeReser = mapperFacade.map(map, KdReser.class);
            if ("removed".equals(map.getState())) {
                KdReser oldReser = kdReserMapper.getInfoById(map.getId());
                /*
                 * 未审核的，都可以删除
                 */
                if (null != oldReser) {
                    // 已经同步的不让修改，如果需要调整 需要打审批
                    if (oldReser.getIsUpdate().equals("1")) {
                        throw new ServiceException("删除失败：第 <font color='red'>" + (i + 1) + " 条结算信息已同步，如需调整请联系财务");
                    } else {
                        // 未同步
                        // 已经审核的只有财务可以删除
                        // 已审核但不是财务 抛出异常
                        if (oldReser.getIsAudit().equals("1") && !caiwu) {
                            throw new ServiceException("删除失败：第 <font color='red'>" + (i + 1) + " 条结算信息已审核，如需调整请联系财务");
                        }
                    }
                    kdReserMapper.deleteById(oldReser);
                }
            } else if ("modified".equals(map.getState())) {
                KdReser oldReser = kdReserMapper.getInfoById(map.getId());
                // 判断是否存在
                if (null != oldReser) {
                    // 已经同步的不让修改，如果需要调整 需要打审批
                    if ("1".equals(oldReser.getIsUpdate())) {
                        boolean backFlag = false;
                        //结算方式
                        if (!oldReser.getIdRemittanceway().equals(kingdeeReser.getIdRemittanceway())) {
                            backFlag = true;
                        }
                        //客户名称
                        if (!oldReser.getCustomername().equals(kingdeeReser.getCustomername())) {
                            backFlag = true;
                        }
                        //客户ID号（团体号 体检卡号 体检号 分中心号）
                        if (!oldReser.getIdCustomer().equals(kingdeeReser.getIdCustomer())) {
                            backFlag = true;
                        }
                        //金额
                        if (!(oldReser.getMoneyamountpaid() + "").equals(kingdeeReser.getMoneyamountpaid() + "")) {
                            backFlag = true;
                        }
                        //销售经理
                        if (!oldReser.getIdFeecharger().equals(kingdeeReser.getIdFeecharger())) {
                            backFlag = true;
                        }
                        if (backFlag) {
                            throw new ServiceException("修改失败：第 <font color='red'>" + (i + 1) + " 条结算信息已同步，如需调整请联系财务");
                        }
                    } else {
                        // 未同步
                        // 已经审核的只有财务可以修改
                        // 已审核但不是财务 抛出异常
                        if (oldReser.getIsAudit().equals("1") && !caiwu) {
                            throw new ServiceException("修改失败：第 <font color='red'>" + (i + 1) + " 条结算信息已审核，如需调整请联系财务");
                        }
                    }
                    if (kingdeeReser.getMoneyamountpaid() == 0) {
                        throw new ServiceException("保存失败：第 <font color='red'>" + (i + 1) + " 条的结算金额不能为0。");
                    }
                    kingdeeReser.setIdChange(SecurityUtils.getUserNo());// 修改人ID
                    kingdeeReser.setModifydate(new Date());// 修改日期
                    kdReserMapper.updateById(kingdeeReser);
                } else {
                    throw new ServiceException("保存失败：第 <font color='red'>" + (i + 1) + " 条结算信息已不存在，已经被删除");
                }
            } else if ("added".equals(map.getState())) {
                if (kingdeeReser.getMoneyamountpaid() == 0) {
                    throw new ServiceException("保存失败：第 <font color='red'>" + (i + 1) + " 条的结算金额不能为0。");
                }
                kingdeeReser.setCreatedate(new Date());// 创建日期
                kingdeeReser.setModifydate(new Date());// 修改日期
                kingdeeReser.setIdCreator(SecurityUtils.getUserNo());// 创建人ID
                kingdeeReser.setIdChange(SecurityUtils.getUserNo());// 修改人ID
                kingdeeReser.setIdRemitter(chargesId);// 流水号ID
                kingdeeReser.setIsAudit("0");// 未审核
                kingdeeReser.setIsUpdate("0");// 未同步
                //这个分中心要取kd_remittance的分中心，不取用户的分中心
                kingdeeReser.setBranchId(kingdeeRemittance.getBranchId());
                list.add(kingdeeReser);
            }
        }
        if (list.size() > 0) {
            kdReserService.saveBatch(list);
        }
        return Boolean.TRUE;
    }


    /**
     * 审核
     *
     * @param rowsId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approve(List<String> rowsId) {
        for (int i = 0; i < rowsId.size(); i++) {
            //获取实体
            KdReser kingdeeReser = kdReserMapper.getInfoById(rowsId.get(i));
            if (null != kingdeeReser) {
                if (kingdeeReser.getIsAudit().equals("1")) {
                    return "有笔流水已审核，无需再审!";
                } else {
                    if (kingdeeReser.getIsUpdate().equals("1")) {
                        return "有笔流水已同步，不能再审!";
                    } else {
                        kingdeeReser.setIsAudit("1");
                        kingdeeReser.setIdAudit(SecurityUtils.getUserNo());
                        // 修改人ID
                        kingdeeReser.setAuditdate(new Date());// 修改日期
                        kdReserMapper.updateById(kingdeeReser);
                    }
                }
            } else {
                return "审核失败，有笔流水已不存在！";
            }
        }
        return "success";
    }

    /**
     * 反审核
     *
     * @param rowsId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String unapprove(List<String> rowsId) {
        //获取实体
        for (int i = 0; i < rowsId.size(); i++) {
            KdReser kingdeeReser = kdReserMapper.getInfoById(rowsId.get(i));
            if (null != kingdeeReser) {
                if (kingdeeReser.getIsAudit().equals("0")) {
                    return "有笔流水未过审，无需反审!";
                } else {
                    if (kingdeeReser.getIsUpdate().equals("1")) {
                        return "有笔流失已同步，不能反审!";
                    } else {
                        //是否审核：0.未审核 1.已审核
                        kingdeeReser.setIsAudit("0");
                        kdReserMapper.updateById(kingdeeReser);
                    }
                }
            } else {
                return "反审失败，有笔流水已不存在！";
            }
        }
        return "success";
    }


    /**
     * 获取销售经理
     * @param param
     * @return
     */
    @Override
    public IPage<FeeChargerDataVo> getFeeChargerData(PageParam<FeeChargerDataVo> page, FeeChargerDataParam param) {
        //取出属性
        String way = param.getWay();
        String key = param.getKey();
        String id = param.getId();

        //去空格
        if (ObjectUtils.isNotEmpty(key)){
            key = key.trim();
        }

        IPage<FeeChargerDataVo> vo = new PageParam<>();
        //获取账户名称
        List<AccountNameDto> paywayList = kdPaywayService.getAccountName(way);
        if (paywayList.size() > 0 ) {
            AccountNameDto os = paywayList.get(0);

            if ("团检-统收".equals(os.getAccountName())) {
                vo = bankSettlementMapper.getFeeChargerData1(page,id,key);
            } else if ("个检-记账".equals(os.getAccountName())||"充卡-银行存款".equals(os.getAccountName())) {
                List<FeeChargerDataVo> list = Arrays.asList(new FeeChargerDataVo("零星客户","零星客户"));
                vo.setRecords(list);
                vo.setTotal(list.size());
                vo.setCurrent(page.getCurrent());
                vo.setSize(page.getSize());
            } else if ("代收体检费-银行存款".equals(os.getAccountName())) {
                vo = bankSettlementMapper.getFeeChargerData2(page,key);
            }

        }

        return vo;
    }

    /**
     * 查询汇总金额
     * @param param
     * @return
     */
    @Override
    public BankAmountVo summaryAmount(BSPageParam param) {
        return bankSettlementMapper.summaryAmount(param);
    }
}

