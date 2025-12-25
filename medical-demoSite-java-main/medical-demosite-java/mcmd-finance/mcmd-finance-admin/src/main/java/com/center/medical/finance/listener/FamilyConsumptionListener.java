package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.FamilyConsumptionEvent;
import com.center.medical.bean.param.OldFamilyConParam;
import com.center.medical.common.config.RsaConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.finance.bean.param.RsaParam;
import com.center.medical.system.service.CodeConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: ay
 * @date: 2024-04-02 14:13
 * @description: 家庭卡消费处理
 */
@Slf4j
@Component("familyConsumptionListener")
@AllArgsConstructor
public class FamilyConsumptionListener {


    private final CodeConfigService codeConfigService;

    @EventListener(FamilyConsumptionEvent.class)
    public void cardCosumeListener(FamilyConsumptionEvent event) {
        log.info("卡消费处理：{}", JSONUtil.toJsonStr(event));
        OldFamilyConParam param = event.getParam();
        if (ObjectUtils.isEmpty(param)){
            throw new ServiceException("请填写完整数据!");
        }
        RsaConfig rsaConfig = codeConfigService.getRsaConfig("11", Constants.RESERVATION_CARD_FLAG);
        if (ObjectUtils.isEmpty(rsaConfig)) {
            throw new ServiceException("请先配置会员卡体检卡RSA非对称加密配置!");
        }
        param.setAuthCode(rsaConfig.getAuthCode());
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), rsaConfig.getPublicKey());
        RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
        log.info("param里面的数据:{}", rsaParam);
        //发送post请求
        String s = HttpUtils.sendPost(Constants.OLD_FAMILYCARD_URL + Constants.OLD_FAMILYCARD_CONSUMPTION, JSONUtil.toJsonStr(rsaParam));
        log.info("体检卡的返回信息:{}", s);
        R r = JSONUtil.toBean(s, R.class);
        if (500 == r.getCode()) {
            throw new ServiceException(r.getMsg());
        }

    }
}
