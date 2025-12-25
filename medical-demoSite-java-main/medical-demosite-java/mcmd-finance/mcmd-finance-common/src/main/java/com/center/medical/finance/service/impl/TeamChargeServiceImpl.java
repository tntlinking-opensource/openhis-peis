package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.CardConsumeType;
import com.center.medical.bean.event.CardConsumeEvent;
import com.center.medical.bean.model.GroupBalance;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.bean.param.CardConsumeParam;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.*;
import com.center.medical.dao.GroupBalanceMapper;
import com.center.medical.dao.PeisReserPaywayMapper;
import com.center.medical.finance.bean.dto.TCFormDataDto;
import com.center.medical.finance.bean.dto.TCGridDataDto;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.vo.TCAddVo;
import com.center.medical.finance.bean.vo.TCExportVo;
import com.center.medical.finance.bean.vo.TCPageVo;
import com.center.medical.finance.dao.TeamChargeMapper;
import com.center.medical.finance.service.TeamChargeService;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import com.center.medical.service.PeisReserPaywayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 团体结算(Peisorgreservation)表服务实现类
 *
 * @author ay
 * @since 2023-04-03 16:32:36
 */
@Slf4j
@Service("teamChargeService")
@RequiredArgsConstructor
public class TeamChargeServiceImpl extends ServiceImpl<TeamChargeMapper, Peisorgreservation> implements TeamChargeService {

    private final TeamChargeMapper teamChargeMapper;
    private final GroupBalanceMapper groupBalanceMapper;
    private final MapperFacade mapperFacade;
    private final PeisReserPaywayMapper peisReserPaywayMapper;
    private final PeisReserPaywayService peisReserPaywayService;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TCPageVo> getList(PageParam<TCPageVo> page, TCPageParam param) {
        param.setFzxId(SecurityUtils.getCId());
        return teamChargeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param param 主键id
     * @return 详情信息
     */
    @Override
    public TCAddVo getInfoById(TCAddParam param) {
        TCAddVo vo = new TCAddVo();
        String id = param.getId();
        if (StringUtils.isNotEmpty(id)) {
            //体检团体结算表
            GroupBalance groupBalance = groupBalanceMapper.getInfoById(id);
            vo.setTypeId(groupBalance.getTypeId());
            vo.setMemo(groupBalance.getMemo());
            //应收金额
            vo.setShouldMoney(groupBalance.getShouldMoney());
            //实收金额
            vo.setRealityMoney(groupBalance.getRealityMoney());
            vo.setId(groupBalance.getId());
        } else {
            vo.setId("");
            vo.setRealityMoney(0d);
            vo.setTypeId("");
            vo.setMemo("");
        }
        vo.setOrderId(param.getOrderId());
        vo.setOrderNum(param.getOrderNum());
        vo.setGroupId(param.getKhdwid());
        vo.setShouldMoney(param.getYs());
        vo.setOrgName(param.getOrgName());
        return vo;
    }

    /**
     * 卡扣款
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateFee(TeamPayParam param) {
        if (param.getRealityMoney() > 0.01) {
            throw new ServiceException("结算金额不能低于0.01元！");
        }
        log.info("结算-卡扣款：{}", param);
        //卡扣款操作
        CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getRealityMoney(), param.getRealityMoney() > 0 ? 2 : 1, param.getMemotext(), SecurityUtils.getUserNo(), param.getIdPayway(), param.getBranchId(), param.getId(), param.getOrgName(), String.valueOf(CardConsumeType.TUANTI.value()), param.getKkfs(), SecurityUtils.getUsername(), null);
        applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        //新增结算记录
        TCSaOrUpParam tcSaOrUpParam = new TCSaOrUpParam();
        //体检者数据
        TCFormDataDto formdata = mapperFacade.map(param, TCFormDataDto.class);
        tcSaOrUpParam.setFormdata(formdata);
        //支付记录
        TCGridDataDto tcGridDataDto = new TCGridDataDto();
        tcGridDataDto.setMoneyamountpaid(-param.getRealityMoney());
        tcGridDataDto.setIdPayway(param.getIdPayway());
        tcGridDataDto.setIsCharged(1);
        tcGridDataDto.setNote(param.getMemotext());
        tcGridDataDto.setState("added");
//        tcGridDataDto.setIsTotalcharge(0);
        tcGridDataDto.setCardno(param.getCardId());
        List<TCGridDataDto> griddata = Arrays.asList(tcGridDataDto);
        tcSaOrUpParam.setGriddata(griddata);
        return saOrUp(tcSaOrUpParam, false);
    }

    /**
     * 退款：体检卡、会员卡等
     *
     * @param param 退款参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean refund(TeamRefundParam param) {
        if (param.getRealityMoney() < 0.01) {
            throw new ServiceException("退款金额不能低于0.01元！");
        }
        //获取支付记录，获取结算记录，判断退款金额是否小于或者等于支付金额，小于则支付记录减去退款金额，等于则直接删除支付记录，然后进行卡金额恢复，结算记录结算金额减去退款金额
        if (StringUtils.isBlank(param.getId())) {
            throw new ServiceException("支付记录ID不能为空！");
        }
        String id = param.getId();
        //获取支付记录
        PeisReserPayway payway = peisReserPaywayMapper.getInfoById(id);
        if (Objects.isNull(payway)) {
            throw new ServiceException("支付记录不存在或者已被删除！");
        }
        if (payway.getIsCharged() == 0) {
            throw new ServiceException("该条支付记录未收费，无法费！");
        }
        //获取结算记录
        GroupBalance balance = groupBalanceMapper.getInfoById(payway.getIdCharge());
        if (Objects.isNull(balance)) {
            throw new ServiceException("该条团检结算记录信息不存在");
        }
        //判断退款金额是否小于或者等于支付金额，小于则支付记录减去退款金额，等于则直接删除支付记录
        if (Arith.sub(payway.getMoneyamountpaid(), param.getRealityMoney()) < 0) {
            //不能超过支付金额
            throw new ServiceException("退款金额(" + param.getRealityMoney() + ")不能超过支付金额(" + payway.getMoneyamountpaid() + ")！");
        }
        if (Arith.sub(payway.getMoneyamountpaid(), param.getRealityMoney()) < 0.01) {
            //直接删除支付记录
            peisReserPaywayMapper.deleteById(payway.getId());
        } else {
            //支付记录减去退款金额
            payway.setMoneyamountpaid(Arith.add(payway.getMoneyamountpaid(), param.getRealityMoney()));
            peisReserPaywayMapper.updateById(payway);
        }
        balance.setRealityMoney(Arith.sub(balance.getRealityMoney(), param.getRealityMoney()));
        groupBalanceMapper.updateById(balance);

        //卡扣款操作
        CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getRealityMoney(), 2, param.getMemotext(), SecurityUtils.getUserNo(), payway.getIdPayway(), param.getBranchId(), payway.getIdCharge(), param.getOrgName(), String.valueOf(CardConsumeType.TUANTI.value()), param.getKkfs(), SecurityUtils.getUsername(), null);
        applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));

        return Boolean.TRUE;
    }


    /**
     * 团体结算保存
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(TCSaOrUpParam param, Boolean flag) {
        TCFormDataDto map = param.getFormdata();
        if (ObjectUtils.isNotEmpty(map)) {
            if (!StringUtils.isBlank(map.getOrderId())) {
                //体检团体结算表
                GroupBalance balance = groupBalanceMapper.getInfoById(map.getId());
                String balanceId = "";
                if (null != balance) {
                    //应收金额
                    balance.setShouldMoney(Double.valueOf(map.getShouldMoney()));
                    //实收金额
                    balance.setRealityMoney(Double.valueOf(map.getRealityMoney()));
                    balance.setOrderId(map.getOrderId());
                    balance.setGroupId(map.getGroupId());
                    groupBalanceMapper.updateById(balance);
                    balanceId = balance.getId();
                } else {
                    balance = new GroupBalance();
                    balance.setShouldMoney(Double.valueOf(map.getShouldMoney()));
                    balance.setRealityMoney(Double.valueOf(map.getRealityMoney()));
                    balance.setOrderId(map.getOrderId());
                    balance.setGroupId(map.getGroupId());
                    groupBalanceMapper.insert(balance);
                    String id = balance.getId();
                    if (StringUtils.isBlank(id)) {
                        throw new ServiceException("保存失败");
                    }
                    balanceId = id;
                }
                // 结算金额
                Double jsMoney = 0d;
                List<TCGridDataDto> featureData = param.getGriddata();
                List<PeisReserPayway> list = new ArrayList<PeisReserPayway>();
                for (int i = 0, l = featureData.size(); i < l; i++) {
                    TCGridDataDto featureMap = featureData.get(i);
                    //体检者结算方式表
                    PeisReserPayway peisReserPayway = mapperFacade.map(featureMap, PeisReserPayway.class);
                    //删除
                    if ("removed".equals(featureMap.getState())) {
                        PeisReserPayway payway = peisReserPaywayMapper.getInfoById(featureMap.getId());
                        // 判断是否存在
                        if (null != payway) {
                            peisReserPaywayMapper.deleteById(payway);
                        }
                    } else if ("modified".equals(featureMap.getState())) {
                        //修改
                        PeisReserPayway payway = peisReserPaywayMapper.getInfoById(featureMap.getId());
                        // 判断是否存在
                        if (null != payway) {
                            peisReserPaywayMapper.updateById(peisReserPayway);
                            jsMoney += payway.getMoneyamountpaid();
                        } else throw new ServiceException("保存失败：第 <font color='red'>" + (i + 1) + " 条付款方式不存在，已经被删除");
                    } else if ("added".equals(featureMap.getState())) {
                        //添加
                        peisReserPayway.setMoneyamountpaiddate(new Date());
                        peisReserPayway.setIdFeecharger(SecurityUtils.getUserNo());
                        peisReserPayway.setIdCharge(balanceId);
                        peisReserPayway.setIsCharged(1);
                        list.add(peisReserPayway);
                        jsMoney += peisReserPayway.getMoneyamountpaid();
                    }
                }
                //体检团体结算表
                GroupBalance balanceNew = groupBalanceMapper.getInfoById(balanceId);
                balanceNew.setRealityMoney(flag ? jsMoney : Arith.add(balanceNew.getRealityMoney(), jsMoney));
                groupBalanceMapper.updateById(balanceNew);
                if (list.size() > 0) {
                    peisReserPaywayService.saveBatch(list);
                }
            } else {
                throw new ServiceException("该条订单信息不存在");
            }
        } else {
            throw new ServiceException("请选择一条记录");
        }
        return Boolean.TRUE;
    }


    /**
     * 导出团体结算数据
     *
     * @param param
     * @return
     */
    @Override
    public List<TCExportVo> getExportData(TCPageParam param) {
        param.setFzxId(SecurityUtils.getCId());
        return teamChargeMapper.getExportData(param);
    }
}

