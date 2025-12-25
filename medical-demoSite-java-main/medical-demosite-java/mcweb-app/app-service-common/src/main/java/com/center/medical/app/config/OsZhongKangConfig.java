package com.center.medical.app.config;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.dto.AESEncryptDto;
import com.center.medical.app.bean.dto.AuthItemSetting;
import com.center.medical.app.bean.model.AuthCode;
import com.center.medical.app.bean.model.AuthItem;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.response.AppResponseEntity;
import com.center.medical.app.common.util.AESUtils;
import com.center.medical.app.common.util.RSAUtil;
import com.center.medical.app.service.AuthCodeService;
import com.center.medical.app.service.AuthItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
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
public class OsZhongKangConfig {

    /**
     * 服务对象
     */
    private final AuthItemService authItemService;
    private final AuthCodeService authCodeService;

    private String authCodeRsa = "6883695d1a2vt926";
    private String authCodeAes = "6883695d1a2vt925";
    private String authFlag = "zk_medical_0920";

    /**
     * 解密及数据清洗
     *
     * @param data 待加密的数据
     * @return
     */
    public String encrypt(String data) {
        log.info("1、沃德小程序业务配置，data：{}", data);
        //查询RSA加密信息
        AuthCode codeConfigRsa = authCodeService.getOne(new LambdaQueryWrapper<AuthCode>().eq(AuthCode::getAuthCode, authCodeRsa)
                .eq(AuthCode::getStatus, 1).gt(AuthCode::getExpiryDate, new Date()));
        log.info("2、沃德小程序业务配置,codeConfigRsa：{}", codeConfigRsa);
        if (Objects.isNull(codeConfigRsa)) {
            log.error("沃德体检系统的RSA授权不存在或者授权已过期！");
            throw new AppBindException("预约失败，请联系客服！");
        }

        AESEncryptDto aesEncryptDto = new AESEncryptDto();
        aesEncryptDto.setAuthCode(codeConfigRsa.getBizAuthCode());
        aesEncryptDto.setFlag(codeConfigRsa.getAuthFlag());
        //RSA加密
        String encrypt = RSAUtil.publicEncrypt(aesEncryptDto.getAuthCode() + data, codeConfigRsa.getKeyText());
        aesEncryptDto.setEncryptData(encrypt);

        //查询AES加密信息
        AuthCode codeConfigAes = authCodeService.getOne(new LambdaQueryWrapper<AuthCode>().eq(AuthCode::getAuthCode, authCodeAes)
                .eq(AuthCode::getStatus, 1).gt(AuthCode::getExpiryDate, new Date()));
        log.info("3、沃德小程序业务配置,codeConfigAes：{}", codeConfigAes);
        if (Objects.isNull(codeConfigAes)) {
            log.error("沃德体检系统的AES授权不存在或者授权已过期！");
            throw new AppBindException("预约失败，请联系客服！");
        }

        String paramValue = "";
        try {
            paramValue = AESUtils.Encrypt(JSONUtil.toJsonStr(aesEncryptDto), codeConfigAes.getKeyText());
        } catch (Exception e) {
            log.error("AES加密失败！");
            throw new AppBindException("预约失败，请联系客服！");
        }
        return paramValue;
    }


    /**
     * V2版解密及数据清洗
     *
     * @param data 待加密的数据
     * @return
     */
    public Map<String, Object> encryptV2(String data) {
        log.info("1、沃德小程序业务配置，data：{}", data);
        //查询RSA加密信息
        AuthCode codeConfigRsa = authCodeService.getOne(new LambdaQueryWrapper<AuthCode>().eq(AuthCode::getAuthCode, authCodeRsa)
                .eq(AuthCode::getStatus, 1).gt(AuthCode::getExpiryDate, new Date()));
        log.info("2、沃德小程序业务配置,codeConfigRsa：{}", codeConfigRsa);
        if (Objects.isNull(codeConfigRsa)) {
            log.error("沃德体检系统的RSA授权不存在或者授权已过期！");
            throw new AppBindException("预约失败，请联系客服！");
        }
        //RSA加密
        String encrypt = RSAUtil.publicEncrypt(codeConfigRsa.getBizAuthCode() + data, codeConfigRsa.getKeyText());
        log.warn("data={}", encrypt);
        Map<String, Object> param = new HashMap<>();
        param.put("code", codeConfigRsa.getBizAuthCode());
        param.put("flag", codeConfigRsa.getAuthFlag());
        param.put("data", URLEncoder.encode(encrypt));
        return param;
    }

    /**
     * 发送请求
     *
     * @param path       请求路径
     * @param method     请求方式：1.GET 2.POST
     * @param paramValue 请求参数
     */
    public AppResponse send(String path, Integer method, String paramValue) {
        AuthItem source = authItemService.getOne(new LambdaQueryWrapper<AuthItem>()
                .eq(AuthItem::getAuthFlag, authFlag)
                .eq(AuthItem::getStatus, 1));
        if (Objects.nonNull(source)) {
//            log.info("沃德小程序接口配置信息,source：{}", source);
            AuthItemSetting setting = JSONUtil.toBean(source.getSetting(), AuthItemSetting.class);
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
                    return AppResponseEntity.fail("发送请求失败!");
                }
                //log.info("沃德小程序发送请求结果,resultStr：{}", resultStr);

                AppResponse result = JSONUtil.toBean(resultStr, AppResponse.class);
//                log.info("沃德小程序发送请求结果,resultStr：{}、result：{}", resultStr, result);
                return result;
            } else {
                log.error("没有沃德小程序接口配置信息！");
                return AppResponseEntity.fail("没有沃德小程序接口配置信息！");
            }

        } else {
            log.error("没有沃德小程序的授权信息！");
            return AppResponseEntity.fail("没有沃德小程序的授权信息！");
        }
    }

    /**
     * 发送请求
     *
     * @param path   请求路径
     * @param method 请求方式：1.GET 2.POST
     * @param param  请求参数
     */
    public AppResponse sendWithMap(String path, Integer method, Map<String, Object> param) {
        AuthItem source = authItemService.getOne(new LambdaQueryWrapper<AuthItem>()
                .eq(AuthItem::getAuthFlag, authFlag)
                .eq(AuthItem::getStatus, 1));
        if (Objects.nonNull(source)) {
//            log.info("沃德小程序接口配置信息,source：{}", source);
            AuthItemSetting setting = JSONUtil.toBean(source.getSetting(), AuthItemSetting.class);
//            log.info("沃德小程序接口配置信息,setting：{}", setting);
            if (Objects.nonNull(setting) && StringUtils.isNotBlank(setting.getHost())) {
                String host = setting.getHost();
//                String host = "http://localhost:8091";
                String resultStr = null;
                try {
                    switch (method) {
                        case 1:
                            resultStr = HttpUtil.get(host + path, param);
                            break;
                        case 2:
                            resultStr = HttpUtil.post(host + path, param);
                            break;
                        default:
                            resultStr = HttpUtil.post(host + path, param);
                            break;
                    }
                } catch (Exception e) {
                    log.error("沃德小程序发送请求失败：{}", e);
                    return AppResponseEntity.fail("发送请求失败!");
                }
                //log.info("沃德小程序发送请求结果,resultStr：{}", resultStr);

                AppResponse result = JSONUtil.toBean(resultStr, AppResponse.class);
//                log.info("沃德小程序发送请求结果,resultStr：{}、result：{}", resultStr, result);
                return result;
            } else {
                log.error("没有沃德小程序接口配置信息！");
                return AppResponseEntity.fail("没有沃德小程序接口配置信息！");
            }

        } else {
            log.error("没有沃德小程序的授权信息！");
            return AppResponseEntity.fail("没有沃德小程序的授权信息！");
        }
    }
}

