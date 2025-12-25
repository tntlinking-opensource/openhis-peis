package com.center.medical.app.open;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.dto.AESEncryptDto;
import com.center.medical.app.bean.model.AuthCode;
import com.center.medical.app.common.util.AESUtils;
import com.center.medical.app.common.util.RSAUtil;
import com.center.medical.app.service.AuthCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 体检系统预约对接接口
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检系统预约对接接口")
@RequestMapping("/test/v1/reservation")
public class TestController {
    /**
     * 服务对象
     */
    private final AuthCodeService authCodeService;
    @Value("${open.aes.skey}")
    private String skey;


    /**
     * 获取参数
     *
     * @param data
     * @return
     */
    @PostMapping("/getparam")
    @ApiOperation(value = "获取参数", notes = "获取参数")
    public String getparam(@RequestParam("data") String data) {
        AESEncryptDto aesEncryptDto = new AESEncryptDto();
        aesEncryptDto.setAuthCode("6883695d3a3a6975");
        aesEncryptDto.setFlag("zk_medical_aes");

        AuthCode one = authCodeService.getOne(new LambdaQueryWrapper<AuthCode>().eq(AuthCode::getAuthCode, aesEncryptDto.getAuthCode()));

        String encrypt = RSAUtil.publicEncrypt(aesEncryptDto.getAuthCode() + data, one.getKeyText());

        aesEncryptDto.setEncryptData(encrypt);
        try {
            return AESUtils.Encrypt(JSONUtil.toJsonStr(aesEncryptDto), skey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

