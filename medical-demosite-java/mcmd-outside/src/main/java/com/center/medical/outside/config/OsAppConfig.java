package com.center.medical.outside.config;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.dto.BusinessSourceSetting;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.constant.SourcesConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.aes.AESUtils;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outside.bean.dto.AESEncryptDto;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.service.BusinessSourceService;
import com.center.medical.system.service.CodeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 沃德小程序业务配置
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OsAppConfig {

    /**
     * 服务对象
     */
    private final CodeConfigService codeConfigService;
    private final BusinessSourceService businessSourceService;

    private String authCodeRsa = "6883695d3a3a6923";
    private String authCodeAes = "6883695d3a3a6924";

    /**
     * 解密及数据清洗
     *
     * @param data 待加密的数据
     * @return
     */
    public R<String> encrypt(String data) {
        //log.info("1、沃德小程序业务配置，data：{}", data);
        //查询RSA加密信息
        CodeConfig codeConfigRsa = codeConfigService.getOne(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getAuthCode, authCodeRsa)
                .eq(CodeConfig::getStatus, 1).gt(CodeConfig::getExpiryDate, new Date()));
        //log.info("2、沃德小程序业务配置,codeConfigRsa：{}", codeConfigRsa);
        if (Objects.isNull(codeConfigRsa)) {
            return R.fail(HttpStatus.NOT_FOUND, "尚未对沃德小程序开放RSA授权或者授权已过期！");
        }

        AESEncryptDto aesEncryptDto = new AESEncryptDto();
        aesEncryptDto.setAuthCode(codeConfigRsa.getBizAuthCode());
        aesEncryptDto.setFlag(codeConfigRsa.getBsFlag());
        //RSA加密
        String encrypt = RSAUtil.publicEncrypt(aesEncryptDto.getAuthCode() + data, codeConfigRsa.getKeyText());
        aesEncryptDto.setEncryptData(encrypt);

        //查询AES加密信息
        CodeConfig codeConfigAes = codeConfigService.getOne(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getAuthCode, authCodeAes)
                .eq(CodeConfig::getStatus, 1).gt(CodeConfig::getExpiryDate, new Date()));
        //log.info("3、沃德小程序业务配置,codeConfigAes：{}", codeConfigAes);
        if (Objects.isNull(codeConfigAes)) {
            return R.fail(HttpStatus.NOT_FOUND, "尚未对沃德小程序开放AES授权或者授权已过期！");
        }

        String paramValue = "";
        try {
            paramValue = AESUtils.Encrypt(JSONUtil.toJsonStr(aesEncryptDto), codeConfigAes.getKeyText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(paramValue);
    }

    /**
     * 发送请求
     *
     * @param path       请求路径
     * @param method     请求方式：1.GET 2.POST
     * @param paramValue 请求参数
     */
    public R send(String path, Integer method, String paramValue) {
        BusinessSource source = businessSourceService.getOne(new LambdaQueryWrapper<BusinessSource>()
                .eq(BusinessSource::getSourceId, SourcesConstants.SOURCE_ID_APP)
                .eq(BusinessSource::getStatus, 1));
        if (Objects.nonNull(source)) {
//            log.info("沃德小程序接口配置信息,source：{}", source);
            BusinessSourceSetting setting = JSONUtil.toBean(source.getSetting(), BusinessSourceSetting.class);
//            log.info("沃德小程序接口配置信息,setting：{}", setting);
            if (Objects.nonNull(setting) && StringUtils.isNotBlank(setting.getHost())) {
                Map<String, Object> mapParam = new HashMap<>();
                mapParam.put("data", paramValue);
                String host = setting.getHost();
                String resultStr = null;
                try {
                    switch (method) {
                        case 1:
                            resultStr = HttpUtil.get(host + path, mapParam);
                            break;
                        case 2:
                            resultStr = HttpUtil.post(host + path, mapParam);
                            break;
                        default:
                            resultStr = HttpUtil.post(host + path, mapParam);
                            break;
                    }
                } catch (Exception e) {
                    log.error("沃德小程序发送请求失败：{}", e);
                    return R.fail("发送请求失败!");
                }
                //log.info("沃德小程序发送请求结果,resultStr：{}", resultStr);

                R result = JSONUtil.toBean(resultStr, R.class);
//                log.info("沃德小程序发送请求结果,resultStr：{}、result：{}", resultStr, result);
                return result;
            } else {
                log.error("没有沃德小程序接口配置信息！");
                return R.fail(HttpStatus.NOT_FOUND, "没有沃德小程序接口配置信息！");
            }

        } else {
            log.error("没有沃德小程序的授权信息！");
            return R.fail(HttpStatus.NOT_FOUND, "没有沃德小程序的授权信息！");
        }
    }
}

