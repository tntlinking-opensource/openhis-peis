package com.center.medical.outside.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.dto.CooCustomerInfo;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BsFlag;
import com.center.medical.common.utils.aes.AESUtils;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.outside.bean.Vo.AuthTokenVO;
import com.center.medical.outside.bean.param.OutsideLoginParam;
import com.center.medical.outside.config.Constants;
import com.center.medical.outside.service.OutsideLoginService;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.service.BusinessSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 路飞船长
 * @date: 2025/6/16 10:21
 * @description:
 */
@Slf4j
@Service
public class OutsideLoginServiceImpl implements OutsideLoginService {

    @Autowired
    private BusinessSourceService businessSourceService;

    @Override
    public R<AuthTokenVO> login(OutsideLoginParam param) {
        log.warn("0、OutsideLoginServiceImpl.login登录参数！{}", param);
        if (ObjectUtils.isEmpty(param) || StrUtil.isBlank(param.getUserName()) || StrUtil.isBlank(param.getPassword())){
            log.warn("1、账号名称或者密码不能为空！");
            return R.fail("账号名称或者密码不能为空！");
        }
        String userName = param.getUserName();
        BusinessSource bss = businessSourceService.getOne(new LambdaQueryWrapper<BusinessSource>().eq(BusinessSource::getAccount, userName).eq(BusinessSource::getStatus, 1));
        if (ObjectUtils.isEmpty(bss)){
            //用户名不存在
            log.warn("2、用户名不存在！");
            return R.fail("账号不存在！");
        }

        // 判断授权码是否是登录用户的授权码
        if (!bss.getSourceId().equals(param.getSourceId())){
            log.warn("3、授权码不是当前登录用户的授权码！{}", param.getSourceId());
            return R.fail("授权码无效!");
        }

        // 半小时内密码输入错误十次，已限制登录30分钟
        String checkPrefix = BsFlag.COO_CUSTOMER+ "_" + Constants.CHECK_VALID_CODE_NUM_PREFIX + param.getSourceId();

        int count = 0;
        if (RedisUtil.hasKey(checkPrefix + userName)) {
            count = RedisUtil.get(checkPrefix + userName);
        }
        if (count > Constants.TIMES_CHECK_INPUT_PASSWORD_NUM) {
            // 半小时内密码输入错误十次，已限制登录30分钟
            log.warn("4、半小时内密码输入错误十次，已限制登录30分钟！");
            return R.fail("半小时内密码输入错误十次，已限制登录30分钟");
        }
        // 半小时后失效
        RedisUtil.set(checkPrefix + userName, count, 1800);

        String password = param.getPassword();
        // 密码不正确
        if (StrUtil.isBlank(password) || !password.equals(bss.getPassword())) {
            count++;
            // 半小时后失效
            RedisUtil.set(checkPrefix + userName, count, 1800);
            // 账号或密码不正确
            log.warn("5、账号或密码不正确！{}、{}、{}", userName, password, bss.getPassword());
            return R.fail("账号或密码不正确");
        }

        //第三方客户授权令牌
        AuthTokenVO authTokenVO = new AuthTokenVO();
        try {
            String token = IdUtil.simpleUUID(); //生成32位的token
            String accessToken = AESUtils.Encrypt(token + System.currentTimeMillis(), Constants.tokenSignKey);
            authTokenVO.setAccessToken(accessToken);
        } catch (Exception e) {
            return R.fail("登录失败，请联系售后技术人员！");
        }
        authTokenVO.setExpiresIn(Constants.ACCESS_TOKEN_EXPIRES_TIME);

        //token携带的客户账号信息
        CooCustomerInfo cooCustomerInfo = new CooCustomerInfo();
        cooCustomerInfo.setAccount(bss.getAccount());
        cooCustomerInfo.setSourceId(bss.getSourceId());
        cooCustomerInfo.setTypeName(bss.getTypeName());
        cooCustomerInfo.setSetting(bss.getSetting());

        // 以token为主键用户信息
        RedisUtil.set(BsFlag.COO_CUSTOMER+ "_ACCESS_TOKEN:" + authTokenVO.getAccessToken(), cooCustomerInfo, Constants.ACCESS_TOKEN_EXPIRES_TIME);

        //返回授权成功信息
        return R.ok(authTokenVO, "登录成功");
    }
}
