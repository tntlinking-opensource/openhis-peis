package com.center.medical.system.config;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.system.bean.dto.SysAuthInfo;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 系统授权管理业务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 15:23:41
 */
@Slf4j
@Component
public class SysAuthConfig {

    @Autowired
    private ISysConfigService iSysConfigService;

    @Value("${system.auth.privateKey}")
    private String privateKey;

    public SysAuthInfo isExpired() {
        SysConfig sysConfig = iSysConfigService.getConfigByKey(Constants.SYS_AUTH_VALUE_KEY);
        SysAuthInfo authInfo = new SysAuthInfo();
        if (Objects.isNull(sysConfig) || StringUtils.isBlank(sysConfig.getConfigValue())) {
            //授权信息不存在，直接返回过期
            authInfo.setExpired(true);
            authInfo.setCanUse(0);
            authInfo.setExpiredTip("系统尚未获取得授权，暂时无法使用");
            authInfo.setIsPop(Boolean.TRUE);
            return authInfo;
        } else {
            String authValue = sysConfig.getConfigValue();
            String data = null;
            try {
                data = RSAUtil.privateDecrypt1(authValue, privateKey);
                log.info("解密信息：{}", data);
                authInfo = JSONUtil.toBean(data, SysAuthInfo.class);
                log.info("授权信息：{}", authInfo);
                if (Objects.isNull(authInfo)){
                    throw new  Exception("系统尚未获得正确授权，暂时无法使用");
                }
            } catch (Exception e) {
                //授权信息解密失败，直接返回过期
                authInfo.setExpired(true);
                authInfo.setCanUse(0);
                authInfo.setExpiredTip("系统尚未获得正确授权，暂时无法使用");
                authInfo.setIsPop(Boolean.TRUE);
                return authInfo;
            }

            if (authInfo.getAuthType() == 0) {
                authInfo.setExpired(false);
                authInfo.setCanUse(1);
                authInfo.setExpiredTip("系统是永久授权！");
                authInfo.setIsPop(Boolean.FALSE);
                return authInfo;
            }

            log.info("是否过期：{}", DateUtil.compare(new Date(), authInfo.getExpiredTime()) == 1);
            Date now = new Date();
            // 比较两个日期
            Integer compareResult = DateUtil.compare(now, authInfo.getExpiredTime());

            if (compareResult < 0) {
                // 未过期
                Long daysRemaining = DateUtil.between(now, authInfo.getExpiredTime(), DateUnit.DAY);
                System.out.println("还剩 " + daysRemaining + " 天过期");
                authInfo.setExpired(false);
                authInfo.setCanUse(1);
                if (daysRemaining < 7) {
                    authInfo.setIsPop(Boolean.TRUE);
                    String message = MessageFormat.format("尊敬的用户，您当前使用软件授权到期时间还有{0}天,请运维人员联系系统授权方，进行授权，到期不授权将影响软件使用。", daysRemaining);
                    authInfo.setExpiredTip(message);
                } else {
                    authInfo.setIsPop(Boolean.FALSE);
                    authInfo.setExpiredTip("系统授权尚在授权期内！");
                }

            } else {
                // 已过期
                Long daysExpired = DateUtil.between(now, authInfo.getExpiredTime(), DateUnit.DAY);
                System.out.println("已过期 " + daysExpired + " 天");
                authInfo.setExpired(true);
                authInfo.setIsPop(Boolean.TRUE);

                if (daysExpired < 7) {
                    authInfo.setCanUse(1);
                    if (authInfo.getCanUse() == 1) {
                        //到期还可用
                        authInfo.setExpiredTip("尊敬的用户，您当前使用软件授权到期时间已超" + daysExpired + "天，请运维人员联系厂家，进行授权，以免影响软件正常使用。");
                    } else {
                        authInfo.setExpiredTip("尊敬的用户，您当前使用软件授权到期时间已超" + daysExpired + "天，如超过7天后未授权，将无法继续使用，请运维人员联系厂家进行授权。");
                    }
                } else {
                    authInfo.setCanUse(authInfo.getCanUse());
                    if (authInfo.getCanUse() == 1) {
                        //到期还可用
                        authInfo.setExpiredTip("尊敬的用户，您当前使用软件授权到期时间已超" + daysExpired + "天，请运维人员联系厂家，进行授权，以免影响软件正常使用。");
                    } else {
                        authInfo.setExpiredTip("尊敬的用户，您当前使用软件授权已到期，已无法继续使用，请运维人员联系厂家进行授权。");
                    }
                }
            }
        }
        return authInfo;
    }

    public static void main(String[] args) {
//        DateTime dateTime = DateUtil.offsetDay(new Date(), -2);
//        System.out.println(DateUtil.between(new Date(), dateTime, DateUnit.DAY));
        String authValue = "n2hIrCLDJZWQZgh4Gpf0s4QUcE/I6RkdJCVAGI3RW6K82tauEw8EX69jrn5cEQC6/wHIzgiz+GvmdJPYFYo24eb3O6YzGyjq/MWs5fDo0Ui8xJOkerZ0GZlLVt8Etn54RxZtYv8MhJozNfpIgKpCClQGbkGtrfPoF0jeN9fX110qwgBrZ6jrQv9fbYLfiE/o2U/pMfjcXb+KbeUUU2IPCpT5KLPtx5YJ2F8SLCXiG/avMk+H/Egt3NaDMjgkrYdBZPjtfFAPf9+FXM4FsvBE2J67R5uRR5PHfXEM/+4oqBdwbiN1teIFWhpJF4Tm90KsUF0cJvPY3AhFx/A0gmmyow==";
        String data = RSAUtil.privateDecrypt(authValue, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPBMxXx8okltQUcWQUcr6FPeNdKP5NjwP+EX7ihDCQBwqiN4aQAd52QaDILZYqD7wvh9obEXGlQ9i/ro4H+Z4nv3rdaohXLrgLR1nvaL6Uz+4kqdbqNPcGxS8dxTTePlDt96PKXsBR650nQ74ac2FskwxeUKmZcCIiirQbK689AfAgMBAAECgYBQFTXHm/21vMyBdWBB6NcfhDGpv7zDs6WGTaRYFKceV23MLmfH2y9vQ+MhhHMDNYQvribBzvI+WtiYwR14SP+aLYefHxnSDZy1GhtwZentiPNz3FuOgG/0EQeSnrlcKuFIckZFIQVLFmQn2RQlGw2P0Hcj/gjvJmnD/R+26pV0OQJBAPr5lltkcerUyhqSObD5rW3fmwf22bUOw5Djni5nI/zZrhoXz5MfvyPiuPoQ74fcZO8qJVhA4fClvK1+tRU1aJsCQQD1HHejxZPl1UVQOZCdoYhBMKt71Du4Mc7RChX7+LslPvDkMmTTQlH7MSTceZe0Aq7TUEh9xEH5mXZCnWCd3+TNAkEArGG+CmwFarxZTMLe6VW072DEhUXh5uCLJvuQbGLfbw687KMqKe2GRcm2L9xuCa0xjqwuFl4KUlYkMC51hmm2vQJBALT97VYwSfKXlhQUPbY4Fg3VK3tHj0WG+emf4fXqh/6LyIMotwzMVw7+DfMDkkKpDIEbMjw0FGeDqzCrVzGcpy0CQCMjSdshZXw75YfWGTITQYdTcBsl4uF55sp10ys9o7UvvrotuDCsgTqXJRbHb6tLMCD0H9t8cjRcnl2hAm4JDU8=");
        log.info("解密信息：{}", data);
    }
}
