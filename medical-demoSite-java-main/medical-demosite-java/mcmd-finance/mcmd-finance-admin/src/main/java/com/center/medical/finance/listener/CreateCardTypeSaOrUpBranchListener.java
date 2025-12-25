package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.event.SaOrUpBranchEvent;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.dao.CardTypeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:47
 * @description: 分中心新增更新监听事件，如果填写了拼音简码，生成会员卡类型,每个分中心只能生成一个，生成后不能修改
 */
@Slf4j
@Component("createCardTypeSaOrUpBranchListener")
@AllArgsConstructor
public class CreateCardTypeSaOrUpBranchListener {

    private final CardTypeMapper cardTypeMapper;

    @EventListener(SaOrUpBranchEvent.class)
    public void createCardTypeListener(SaOrUpBranchEvent event) {
        log.info("分中心新增更新监听事件,生成会员卡类型：{}", JSONUtil.toJsonStr(event));
        Integer flag = event.getFlag();
        if (Objects.equals(flag, 1)) {
            //如果填写了拼音简码，生成会员卡类型,每个分中心只能生成一个，生成后不能修改
            if (StringUtils.isNotEmpty(event.getPyjm())) {
                CardType ct = new CardType();
                ct.setType(1);//会员卡
                ct.setCardPrefix(event.getPyjm() + event.getJm());
                ct.setTypeName(event.getFzx() + "会员卡");
                ct.setIsRecharge(1);
                ct.setMemo(event.getFzx() + "会员卡");
                ct.setCid(event.getBranchId());
                cardTypeMapper.insert(ct);
            }
        } else {
            CardType oldType = cardTypeMapper.selectOne(new LambdaQueryWrapper<CardType>().eq(CardType::getCid, event.getBranchId()));
            if (oldType == null) {
                CardType ct = new CardType();
                ct.setType(1);//会员卡
                ct.setCardPrefix(event.getPyjm() + event.getJm());
                ct.setTypeName(event.getFzx() + "会员卡");
                ct.setIsRecharge(1);
                ct.setMemo(event.getFzx() + "会员卡");
                ct.setCid(event.getBranchId());
                cardTypeMapper.updateById(ct);
            }

        }
    }
}
