package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.CardConsumeEvent;
import com.center.medical.bean.param.CardConsumeParam;
import com.center.medical.finance.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:47
 * @description: 会员卡消费处理
 */
@Slf4j
@Component("cardCosumeListener")
@AllArgsConstructor
public class CardConsumeListener {

    private final CardService cardService;

    @EventListener(CardConsumeEvent.class)
    public void cardCosumeListener(CardConsumeEvent event) {
        log.info("卡消费处理：{}", JSONUtil.toJsonStr(event));
        CardConsumeParam param = event.getParam();
        if (Objects.nonNull(param)) {
            String id = cardService.saveOrUpdateFee(param);
            log.info("卡消费处理结果：{}", id);
            param.setConsumeId(id);
        }
    }
}
