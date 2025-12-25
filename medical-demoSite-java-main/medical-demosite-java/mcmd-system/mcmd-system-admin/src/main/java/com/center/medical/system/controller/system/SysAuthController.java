package com.center.medical.system.controller.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.system.bean.dto.SysAuthInfo;
import com.center.medical.system.bean.model.SysAuthLog;
import com.center.medical.system.bean.param.AuthParam;
import com.center.medical.system.bean.param.SysAuthLogParam;
import com.center.medical.system.config.SysAuthConfig;
import com.center.medical.system.service.SysAuthLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * 系统授权管理业务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 15:23:41
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Api(tags = "系统授权管理")
@RequestMapping("system/auth")
public class SysAuthController extends BaseController {

    private final SysAuthConfig sysAuthConfig;
    private final SysAuthLogService sysAuthLogService;

    /**
     * 生成非堆成加密信息
     *
     * @param param 参数
     * @return 加密信息
     */
    @GetMapping("/generateRSA")
    @ApiOperation(value = "生成非堆成加密信息", notes = "生成非堆成加密信息")
    public R<Map<String, String>> generateRSA(AuthParam param) {
        Map<String, String> keys = RSAUtil.createKeys(1024);
        System.out.println("生成密钥对：" + JSONUtil.toJsonStr(keys));
        return R.ok(keys);
    }

    /**
     * 生成授权信息
     *
     * @param param 参数
     * @return 加密信息
     */
    @GetMapping("/generate")
    @ApiOperation(value = "生成授权信息", notes = "生成授权信息")
    public R<SysAuthLog> generate(SysAuthLogParam param) {
        if (StringUtils.isBlank(param.getInstitutionId())) {
            throw new GlobalException("未选择授权的机构！");
        }
        if (param.getAuthType() == 1) {
            if (Objects.isNull(param.getExpiredTime())) {
                throw new GlobalException("到期时间不能为空！");
            }
            if (Objects.isNull(param.getCanUse())) {
                throw new GlobalException("没有设置到期是否可用！");
            }
        }
        return R.ok(sysAuthLogService.generate(param));

    }

    /**
     *
     * 生成授权信息
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        System.out.println("生成的auth_code");
//        System.out.println(generateRandomCode());
//        System.out.println(DateUtil.parse("2099-01-01", "yyyy-MM-dd"));
        SysAuthInfo authInfo = new SysAuthInfo();
        authInfo.setAuthType(1);
        authInfo.setExpired(false);
        authInfo.setCanUse(1);
        authInfo.setExpiredTip("系统可以正常使用");
        authInfo.setIsPop(Boolean.FALSE);
        authInfo.setExpiredTime(DateUtil.parse("2028-1-17", "yyyy-MM-dd"));
        System.out.println("授权信息："+JSONUtil.toJsonStr(authInfo));

        String authValue = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(authInfo), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYgyBz6TQfJg9UfWymhHlFSOwo+i5Drk0hmO92RVMoKXoS8SmcljvfEb19CX+UHcHuzpHB+lkxAva8mT78k3q65gxNOTT8ZKHpP3qZxo3T65wDYfHpqtiFc1Vv/1lJ6CnuOB6OMnmgL522a95pSMbTb4GntwDCle3fl9xVEt6+YwIDAQAB");
        System.out.println("加密信息："+authValue);
        String data = RSAUtil.privateDecrypt1(authValue, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJiDIHPpNB8mD1R9bKaEeUVI7Cj6LkOuTSGY73ZFUygpehLxKZyWO98RvX0Jf5Qdwe7OkcH6WTEC9ryZPvyTerrmDE05NPxkoek/epnGjdPrnANh8emq2IVzVW//WUnoKe44Ho4yeaAvnbZr3mlIxtNvgae3AMKV7d+X3FUS3r5jAgMBAAECgYAFSrd7jiHy2r05b0iv1nTmxIr9OQEV9BxVCBt2/RzPjfDga2HIPyZArTrn9cZpe0bZZG2GT4t8ud+Z169p0Cw39kD6k1IJUnFZQB2E5hfjUQEVDMi2vJ6yFoMtsIu3tsGfhg28E98f0gZhRF/vN47CGX3docXHgASeG5UNV9tJIQJBAMeDAsDxeSEDtHYuTVLyXTmfS3uONFQDrVJJZZ5tteOii0RsjIVpjfHtSK1gNCVUr2BOJI0YM2VMTfBW9+vb+ysCQQDDsYAF0Q/uby7+H2D79kRisCjxOTVNNrTee+tWoT+IF+dg+n3HuIVj8VpLwR/OVyk1tNlwOQVrKGwH+z2HTk2pAkEAiSZqsjBNUIC3gbZ6uVaa942l2h37ovWUJEkIbkyFCSMoEbV9NAlH5tNzbpv/vNnmZZHSVpwYWL6HpXxjmePzWQJAMGo7uDSu8liDkc+FmRCmfpwCpRt4OnAsc1bJNJeUNkFjtzSB5eWa6s0Nf+x5x/z8bxMdMIyK8KYBhD/+SvlucQJAI5JCqvaTqDx462am1+Pe1Apwh4ITA4luVJijTsqzibxw1BlhxM+YmoucVky14nDXnCuy4PeZ3SXRan2M5/+yug==");
        System.out.println("解密信息："+data);
        SysAuthInfo authInfo1 = JSONUtil.toBean(data, SysAuthInfo.class);
        System.out.println("授权信息："+JSONUtil.toJsonStr(authInfo1));
    }



    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // 生成16位的代码，每位是一个16进制字符
        for (int i = 0; i < 16; i++) {
            int randomInt = random.nextInt(16); // 随机生成0-15之间的整数
            char randomChar = (char) (randomInt < 10 ? '0' + randomInt : 'A' + randomInt - 10);
            code.append(randomChar);
        }

        return code.toString();
    }

    /**
     * 判断授权是否过期
     *
     * @return 判断结果
     */
    @PostMapping("/isExpired")
    @ApiOperation(value = "判断授权是否过期", notes = "判断授权是否过期")
    public R isExpired() {
//        SysAuthInfo authInfo = new SysAuthInfo();
//        授权信息不存在，直接返回过期
//        authInfo.setExpired(false);
//        authInfo.setCanUse(0);
//        authInfo.setExpiredTip("系统可以正常使用");
//        authInfo.setIsPop(Boolean.FALSE);
//        authInfo.setExpiredTime(DateUtil.parse("2025-06-30", "yyyy-MM-dd"));
//        return R.ok(authInfo);
        return R.ok(sysAuthConfig.isExpired());
    }
}
