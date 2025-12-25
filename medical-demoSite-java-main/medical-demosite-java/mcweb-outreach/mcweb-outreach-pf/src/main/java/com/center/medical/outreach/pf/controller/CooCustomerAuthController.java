package com.center.medical.outreach.pf.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.center.medical.bean.dto.CooCustomerInfo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.aes.AESUtils;
import com.center.medical.outside.bean.Vo.AuthTokenVO;
import com.center.medical.outreach.bean.param.CooCustomerLoginParam;
import com.center.medical.outside.bean.param.OutsideLoginParam;
import com.center.medical.outside.service.OutsideLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 合作单位登录授权，生成授权令牌
 *
 * @author 路飞船长
 * @since 2024-11-06 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "合作单位登录")
@RequestMapping("/open/api/v3")
public class CooCustomerAuthController extends BaseController {
    /**
     * 服务对象
     */
    private final OutsideLoginService outsideLoginService;

    /**
     * 生成授权令牌access_token：生成、续期、销毁
     * access_token中存储着客户授权码、客户单位信息、订单信息
     */

    @PostMapping("/login")
    @ApiOperation(value = "合作单位登录", notes = "合作单位登录")
    public R<AuthTokenVO> login(HttpServletRequest request) {
        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        Object dataStr = request.getAttribute("dataStr");
        log.info("合作单位登录,data：{}", dataStr);
        CooCustomerLoginParam params = JSON.parseObject((String) dataStr, CooCustomerLoginParam.class);
        if (ObjectUtils.isEmpty(params)){
            //参数不能为空
            return R.fail("参数不能为空");
        }
        OutsideLoginParam param = new OutsideLoginParam();
        param.setUserName(params.getUserName());
        param.setPassword(params.getPassword());
        param.setSourceId(authInfo.getSourceId());
        return outsideLoginService.login(param);

//        String userName = params.getUserName();
//        BusinessSource bss = businessSourceService.getOne(new LambdaQueryWrapper<BusinessSource>().eq(BusinessSource::getAccount, userName).eq(BusinessSource::getStatus, 1));
//        if (ObjectUtils.isEmpty(bss)){
//            //用户名不存在
//            return R.fail("账号不存在");
//        }
//
//        // 判断授权码是否是登录用户的授权码
//        if (!bss.getSourceId().equals(authInfo.getSourceId())){
//            log.warn("9、授权码不是当前登录用户的授权码！");
//            return R.fail("授权码无效!");
//        }
//
//        // 半小时内密码输入错误十次，已限制登录30分钟
//        String checkPrefix = BsFlag.COO_CUSTOMER+ "_" + Constants.CHECK_VALID_CODE_NUM_PREFIX + authInfo.getSourceId();
//
//        int count = 0;
//        if (RedisUtil.hasKey(checkPrefix + userName)) {
//            count = RedisUtil.get(checkPrefix + userName);
//        }
//        if (count > Constants.TIMES_CHECK_INPUT_PASSWORD_NUM) {
//            // 半小时内密码输入错误十次，已限制登录30分钟
//            return R.fail("半小时内密码输入错误十次，已限制登录30分钟");
//        }
//        // 半小时后失效
//        RedisUtil.set(checkPrefix + userName, count, 1800);
//
//        String password = params.getPassword();
//        // 密码不正确
//        if (StrUtil.isBlank(password) || !password.equals(bss.getPassword())) {
//            count++;
//            // 半小时后失效
//            RedisUtil.set(checkPrefix + userName, count, 1800);
//            // 账号或密码不正确
//            return R.fail("账号或密码不正确");
//        }
//
//        //第三方客户授权令牌
//        AuthTokenVO authTokenVO = new AuthTokenVO();
//        try {
//            String token = IdUtil.simpleUUID(); //生成32位的token
//            String accessToken = AESUtils.Encrypt(token + System.currentTimeMillis(), Constants.tokenSignKey);
//            authTokenVO.setAccessToken(accessToken);
//        } catch (Exception e) {
//            return R.fail("登录失败，请联系售后技术人员！");
//        }
//        authTokenVO.setExpiresIn(Constants.ACCESS_TOKEN_EXPIRES_TIME);
//
//        //token携带的客户账号信息
//        CooCustomerInfo cooCustomerInfo = new CooCustomerInfo();
//        cooCustomerInfo.setAccount(bss.getAccount());
//        cooCustomerInfo.setSourceId(bss.getSourceId());
//        cooCustomerInfo.setTypeName(bss.getTypeName());
//        cooCustomerInfo.setSetting(bss.getSetting());
//
//        // 以token为主键用户信息
//        RedisUtil.set(BsFlag.COO_CUSTOMER+ "_ACCESS_TOKEN:" + authTokenVO.getAccessToken(), cooCustomerInfo, Constants.ACCESS_TOKEN_EXPIRES_TIME);
//
//        //返回授权成功信息
//        return R.ok(authTokenVO, "登录成功");
    }

    public static void main(String[] args) throws Exception {
        String accessToken = IdUtil.simpleUUID();
        System.out.println(accessToken);
        String encrypt = AESUtils.Encrypt(accessToken + System.currentTimeMillis(), "343354b655c9cefa");
        System.out.println(encrypt);
        System.out.println(AESUtils.Decrypt(encrypt, "343354b655c9cefa"));
    }

}

