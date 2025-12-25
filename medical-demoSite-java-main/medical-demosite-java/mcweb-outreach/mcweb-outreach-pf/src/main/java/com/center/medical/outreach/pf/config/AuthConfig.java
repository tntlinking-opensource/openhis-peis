package com.center.medical.outreach.pf.config;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.config.RsaConfig;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.aes.AESUtils;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outreach.bean.dto.CommonDto;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.service.CodeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 外联系统标准接口请求接收权限认证处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthConfig {

    /**
     * 服务对象
     */
    private final CodeConfigService codeConfigService;

    /**
     * 解密及数据清洗
     *
     * @param data         加密的数据
     * @param aesFlag
     * @param authcodeFlag
     * @param rsaFlag
     * @return
     */
    public R<String> commonAuth(String data, String aesFlag, String authcodeFlag, String rsaFlag) {
        //log.info("解密及数据清洗：{}、{}、{}、{}", data, aesFlag, authcodeFlag, rsaFlag);

        //接收数据
        CodeConfig aesConfig = codeConfigService.getObject(null, null, aesFlag);
        //log.info("解密及数据清洗AESConfig：{}", aesConfig);
        if (Objects.isNull(aesConfig)) {
            return R.fail(HttpStatus.NOT_FOUND, "该功能尚未开放！");
        }
        CommonDto commonDto = null;
        try {
            //AES解密
            String decrypt = AESUtils.Decrypt(data, aesConfig.getValueText());
            //log.info("解密及数据清洗AES解密：{}", decrypt);
            commonDto = JSONUtil.toBean(decrypt, CommonDto.class);

        } catch (Exception e) {
            return R.fail(HttpStatus.BAD_REQUEST, "数据不合法！");
        }

        if (Objects.isNull(commonDto) || StringUtils.isBlank(commonDto.getEncryptData())) {
            return R.fail(HttpStatus.BAD_REQUEST, "参数不合法！");
        }

        //校验授权码是否有效
        if (!codeConfigService.isWorking(commonDto.getFlag(), commonDto.getAuthCode(), authcodeFlag)) {
            return R.fail(HttpStatus.FORBIDDEN, "授权已过期！");
        }
        // RSA解密
        RsaConfig rsaConfig = codeConfigService.getRsaConfig(commonDto.getFlag(), rsaFlag);
        //log.info("解密及数据清洗rsaConfig：{}", rsaConfig);
        if (Objects.isNull(rsaConfig)) {
            return R.fail(HttpStatus.UNAUTHORIZED, "尚未获得授权！");
        }

        if (StringUtils.isBlank(rsaFlag)) {
            //没有请求参数，无需解密
            return R.ok();
        }
        String decryptData = RSAUtil.privateDecrypt(commonDto.getEncryptData(), rsaConfig.getPrivateKey());
        //log.info("解密及数据清洗，请求参数：{}", decryptData);

        return R.ok(decryptData);
    }


}

