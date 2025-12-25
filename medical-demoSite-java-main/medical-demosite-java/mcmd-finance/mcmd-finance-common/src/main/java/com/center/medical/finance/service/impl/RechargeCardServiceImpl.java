package com.center.medical.finance.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.LimitOperation;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.LimitOperationMapper;
import com.center.medical.finance.bean.dto.RCFormDataDto;
import com.center.medical.finance.bean.dto.RCGridDataDto;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardPayway;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.bean.param.RCSaOrUpParam;
import com.center.medical.finance.bean.param.RechargeCardParam;
import com.center.medical.finance.bean.param.SaOrUpFeeParam;
import com.center.medical.finance.bean.vo.RechargeCardVo;
import com.center.medical.finance.dao.CardMapper;
import com.center.medical.finance.dao.CardPaywayMapper;
import com.center.medical.finance.dao.CardTypeMapper;
import com.center.medical.finance.dao.RechargeCardMapper;
import com.center.medical.finance.service.CardPaywayService;
import com.center.medical.finance.service.RechargeCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 体检卡(Card)表服务实现类
 *
 * @author ay
 * @since 2023-03-16 11:30:02
 */
@Slf4j
@Service("rechargeCardService")
@RequiredArgsConstructor
public class RechargeCardServiceImpl extends ServiceImpl<RechargeCardMapper, Card> implements RechargeCardService {

    private final RechargeCardMapper rechargeCardMapper;
    private final CardMapper cardMapper;
    private final CardTypeMapper cardTypeMapper;
    private final LimitOperationMapper limitOperationMapper;
    private final MapperFacade mapperFacade;
    private final CardPaywayMapper cardPaywayMapper;
    private final CardPaywayService cardPaywayService;

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    @Override
    public IPage<RechargeCardVo> getList(PageParam<RechargeCardVo> page, RechargeCardParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //去空格
        if (ObjectUtils.isNotEmpty(param.getCardId())) {
            param.setCardId(param.getCardId());
        }
        return rechargeCardMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Card getInfoById(String id) {
        return rechargeCardMapper.getInfoById(id);
    }

    ;

    /**
     * 获取体检卡卡相关内容
     *
     * @param cardId
     * @return
     */
    @Override
    public HashMap getCardData(String cardId) {
        //体检卡
        Card card = cardMapper.selectOne(new QueryWrapper<Card>()
                .eq("card_no", cardId).eq("is_delete", 0));
        HashMap result = new HashMap();
        if (card != null) {
            //卡类型
            CardType caty = cardTypeMapper.getInfoById(card.getTypeId());
            if (caty != null) {
                result.put("cardType", card.getTypeId());
                result.put("cardTypeName", caty.getTypeName());
                result.put("kbs", card.getCardLogo());
                result.put("kbz", card.getMemo());
                result.put("cardState", card.getMemo());
                result.put("ksm", card.getCardState());
                result.put("kyje", null == card.getBalanceLimit() ? 0 : card.getBalanceLimit());
                //有效期
                Date yxq = card.getValidity() == null ? null : card.getValidity();
                result.put("yxq", yxq);
                if (caty.getType() == 1) {
                    result.put("sign", 3);
                } else if (caty.getIsRecharge() == 1) {
                    result.put("sign", 1);
                } else {
                    //不能充值
                    result.put("sign", 2);
                }
            }
        } else {
            //卡号不存在
            result.put("sign", 0);
        }
        return result;
    }


    /**
     * 保存充值
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(RCSaOrUpParam param) {
        RCFormDataDto map = param.getFormdata();
        //积分
        Double lim = Double.parseDouble((map.getLimit()).toString());
        //CW卡额度操作表
        LimitOperation odis = mapperFacade.map(map, LimitOperation.class);

        String name = "";
        // 判断是否是从内网获取数据，操作人
        if (null != map.getUserName()) {
            name = map.getUserName();
        } else {
            //获取当前登录用户名称
            String userId = SecurityUtils.getUserNo();
            //获取当前登录用户名
            name = SecurityUtils.getUsername();
        }
        // 判断是否为空
        String cardId = "";
        if (StringUtils.isBlank(odis.getId())) {
            //体检卡
            Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", odis.getCardId()));
            if (ObjectUtils.isEmpty(card)) {
                throw new ServiceException("该卡号 " + odis.getCardId() + " 不存在");
            }
            if (card.getBalanceLimit() == null) {
                card.setBalanceLimit(0.0);// 如果是null值，赋值0.0可以充值
            }
            //积分相加
            Double a = card.getBalanceLimit() + lim;
            card.setBalanceLimit(a);
            odis.setHandleMoney(a);
            //当前登录用户
            odis.setOperationId(name);
            //设置isDelete字段为0
            odis.setIsDelete(0);
            odis.setIsAdd(0);
            Date now = new Date();
            odis.setOperationTime(now);
            limitOperationMapper.insert(odis);
            String id = odis.getId();
            if (StringUtils.isBlank(id)) {
                throw new ServiceException("保存失败");
            }
            cardMapper.updateById(card);
            cardId = odis.getId();
        }
        // 充值方式
        // 收款金额
        Double jsMoney = 0d;
        List<RCGridDataDto> featureData = param.getGriddata();
        List<CardPayway> list = new ArrayList<CardPayway>();
        for (int i = 0, l = featureData.size(); i < l; i++) {
            RCGridDataDto featureMap = featureData.get(i);
            //快速赋值
            CardPayway cardPayway = mapperFacade.map(featureMap, CardPayway.class);
            if ("removed".equals(featureMap.getState())) {
                //删除
                CardPayway payway = cardPaywayMapper.getInfoById(featureMap.getId());
                // 判断是否存在
                if (null != payway) {
                    cardPaywayMapper.deleteById(payway);
                }
            } else if ("modified".equals(featureMap.getState())) {
                //修改
                CardPayway payway = cardPaywayMapper.getInfoById(featureMap.getId());
                // 判断是否存在
                if (null != payway) {
                    //更新
                    cardPaywayMapper.updateById(cardPayway);
                    jsMoney += payway.getMoneyamountpaid();
                } else throw new ServiceException("保存失败：第 <font color='red'>" + (i + 1) + " 条付款方式不存在，已经被删除");
            } else if ("added".equals(featureMap.getState())) {
                //添加
                cardPayway.setMoneyamountpaiddate(new Date());
                cardPayway.setIdFeecharger(SecurityUtils.getUserNo());
                cardPayway.setIdCharge(cardId);
                cardPayway.setIsCharged(1);
                list.add(cardPayway);
                jsMoney += cardPayway.getMoneyamountpaid();
            }
        }
        if (list.size() > 0) {
            //批量保存
            cardPaywayService.saveBatch(list);
        }
        return Boolean.TRUE;
    }

    /**
     * 体检卡消费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateFee(SaOrUpFeeParam param) {

        Double mon = param.getKyje();
        Double lim = param.getLimit();
        //相减
        Double a = mon - lim;
        if (a < 0) {
            throw new ServiceException("保存失败:可用金额不足");
        }
        //快速赋值
        LimitOperation odis = mapperFacade.map(param, LimitOperation.class);
        //获取当前用户名
        //String userName=toolUtil.userName();
        String name = "";
        // 判断是否是从内网获取数据，操作人
        if (null != param.getUserName()) {
            name = param.getUserName();
        } else {
            name = SecurityUtils.getUsername();
        }
        // 判断是否为空
        if (StringUtils.isBlank(odis.getId())) {
            //体检卡
            Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", odis.getCardId()));
            if (null != card) {
                if (card.getBalanceLimit() < lim) {
                    throw new ServiceException("保存失败:可用金额不足");
                }
                // TODO: wait 用到了AppUser表
//                if(get(AppUserCard.class, Restrictions.eq("cardId",card.getId()))!=null){
//                    throw new ServiceException("保存失败:体检卡已绑定");
//                }
            } else {
                throw new ServiceException("保存失败:该体检卡不存在");
            }
            card.setBalanceLimit(a);
            odis.setOperationId(name);
            odis.setHandleMoney(a);
            //设置isDelete字段为0
            odis.setIsDelete(0);
            odis.setIsAdd(1);
            Date now = new Date();
            odis.setOperationTime(now);
            odis.setChargeId(param.getChargeId() == null ? null : param.getChargeId());
            odis.setConsumetype(param.getConsumeType() == null ? null : param.getConsumeType());
            limitOperationMapper.insert(odis);
            String id = odis.getId();
            if (!StringUtils.isBlank(id)) {
                cardMapper.updateById(card);
            } else {
                throw new ServiceException("保存失败");
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 体检卡消费明细
     *
     * @param param
     * @return
     */
    @Override
    public List<RechargeCardVo> getExportData(RechargeCardParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //去空格
        if (ObjectUtils.isNotEmpty(param.getCardId())) {
            param.setCardId(param.getCardId());
        }
        return rechargeCardMapper.getExportData(param);
    }
}

