package com.center.medical.app.security.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.IPHelper;
import com.center.medical.app.config.WxConfig;
import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import com.center.medical.app.security.common.enums.SocialType;
import com.center.medical.app.security.common.enums.SysTypeEnum;
import com.center.medical.app.security.common.manager.TokenStore;
import com.center.medical.app.security.common.model.AppConnect;
import com.center.medical.app.security.common.service.AppConnectService;
import com.center.medical.app.security.common.vo.TokenInfoVO;
import com.center.medical.app.security.dto.BindSocialDTO;
import com.center.medical.app.service.SmsLogService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 尝试使用社交账户登录
 * 如果登录成功，返回token信息
 * 如果登录失败，返回一个临时的uid，同时把openid之类的信息保存到数据库
 *
 * @author FrozenWatermelon
 * @date 2021/1/16
 */
@RequestMapping("/social")
@RestController
@Api(tags = "社交账号登录")
public class SocialLoginController {


    @Autowired
    private WxConfig wxConfig;
    @Autowired
    private AppConnectService appConnectService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private SmsLogService smsLogService;
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }


    /**获取手机号*/
    public static final String WX_GET_PHONE_NUMBER_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";

    /**
     * 返回状态码 476 账号未注册，前端看到这个状态码，弹出选择框，提示用户账号未注册，是否进入注册页面，用户选择是，进入注册页面
     *
     * @param bindSocial
     * @return
     */
    @PostMapping("/bind")
    @ApiOperation(value = "绑定社交账号", notes = "返回状态码 476 账号未注册，前端看到这个状态码，弹出选择框，提示用户账号未注册，是否进入注册页面，用户选择是，进入注册页面")
    public ResponseEntity<?> bind(@RequestBody BindSocialDTO bindSocial) {
        if (!smsLogService.checkValidCode(bindSocial.getValidAccount(), bindSocial.getValidCode(), SendType.VALID)) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }
        AppConnect appConnect = appConnectService.getByTempUid(bindSocial.getTempUid());
        if (appConnect == null || appConnect.getUserId() != null) {
            return ResponseEntity.badRequest().body("请关闭应用后重新打开进行操作~");
        }

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, bindSocial.getValidAccount()));
        if (user == null) {
            return ResponseEntity.status(AppHttpStatus.ACCOUNT_NOT_REGISTER.value()).build();
        }
        String userId = user.getUserId();
        long bindCount = appConnectService.count(new LambdaQueryWrapper<AppConnect>().eq(AppConnect::getUserId, userId).eq(AppConnect::getAppId, appConnect.getAppId()));
        if (bindCount > 0) {
            return ResponseEntity.badRequest().body("该账号已被绑定，请换个账号试试~");
        }
        // 绑定userId
        appConnect.setUserId(userId);
        appConnectService.updateById(appConnect);

        return ResponseEntity.ok(getTokenVo(appConnect));
    }

    /**
     * 返回状态码 475 社交账号未绑定，当前端看到该异常时，应该在合适的时间（比如在购买的时候跳）根据社交账号的类型，跳转到绑定系统账号的页面
     *
     * @param code
     * @return
     * @throws WxErrorException
     */
//    @PostMapping("/mp")
//    @ApiOperation(value = "公众号登录", notes = "通过公众号进行登录")
//    public ResponseEntity<?> mp(@RequestBody String code) throws WxErrorException {
//        Integer socialType = SocialType.MP.value();
//        WxOAuth2AccessToken wxAccessToken = wxConfig.getWxMpService().getOAuth2Service().getAccessToken(code);
//
//        AppConnect appConnect = appConnectService.getByBizUserId(wxAccessToken.getOpenId(), socialType);
//        if (appConnect != null && appConnect.getUserId() != null) {
//            return ResponseEntity.ok(getTokenVo(appConnect));
//        }
//        String tempUid = IdUtil.simpleUUID();
//        WxOAuth2UserInfo wxUserInfo = wxConfig.getWxMpService().getOAuth2Service().getUserInfo(wxAccessToken, null);
//        if (appConnect == null) {
//            appConnect = new AppConnect();
//        }
//        appConnect.setAppId(socialType);
//        appConnect.setTempUid(tempUid);
//        appConnect.setNickName(wxUserInfo.getNickname());
//        appConnect.setImageUrl(wxUserInfo.getHeadImgUrl());
//        appConnect.setBizUserId(wxUserInfo.getOpenid());
//        appConnect.setBizUnionid(wxUserInfo.getUnionId());
//        appConnectService.saveOrUpdate(appConnect);
//
//        return ResponseEntity.status(YamiHttpStatus.SOCIAL_ACCOUNT_NOT_BIND.value()).body(tempUid);
//    }


    /**
     * 返回状态码 475 社交账号未绑定，当前端看到该异常时，应该在合适的时间（比如在购买的时候跳）根据社交账号的类型，跳转到绑定系统账号的页面
     *
     * @param code
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/ma")
    @ApiOperation(value = "小程序登录", notes = "通过小程序进行登录，返回状态码 475 社交账号未绑定，当前端看到该异常时，应该在合适的时间（比如在购买的时候跳）根据社交账号的类型，跳转到绑定系统账号的页面")
    @ApiImplicitParam(name = "code", value = "小程序端用户登录授权码")
    public ResponseEntity<?> ma(@RequestParam(value = "code") String code) throws WxErrorException {
        Integer socialType = SocialType.MA.value();
        WxMaJscode2SessionResult session = wxConfig.getWxMaService().getUserService().getSessionInfo(code);

        AppConnect appConnect = appConnectService.getByBizUserId(session.getOpenid(), socialType);
        if (appConnect != null && StringUtils.isNotEmpty(appConnect.getUserId())) {
            return ResponseEntity.ok(getTokenVo(appConnect));
        }
        String tempUid = IdUtil.simpleUUID();
        if (appConnect == null) {
            appConnect = new AppConnect();
        }
        appConnect.setAppId(socialType);
        appConnect.setTempUid(tempUid);
        appConnect.setBizTempSession(session.getSessionKey());
        appConnect.setBizUserId(session.getOpenid());
        appConnect.setBizUnionid(session.getUnionid());
        appConnectService.saveOrUpdate(appConnect);

        return ResponseEntity.status(AppHttpStatus.SOCIAL_ACCOUNT_NOT_BIND.value()).body(tempUid);
    }

    private TokenInfoVO getTokenVo(AppConnect appConnect) {
        User user = userService.getUserByUserId(appConnect.getUserId());
        UserInfoInTokenBO data = new UserInfoInTokenBO();
        data.setUserId(user.getUserId());
        data.setSysType(SysTypeEnum.ORDINARY.value());
        data.setBizUid(appConnect.getBizUnionid());
        data.setBizUserId(appConnect.getBizUserId());
        data.setSessionKey(appConnect.getBizTempSession());
        data.setSocialType(appConnect.getAppId());
        data.setEnabled(user.getStatus() == 1);
        // 保存token，返回token数据给前端
        return tokenStore.storeAndGetVo(data);
    }


    /**
     *
     *  解析用户手机号登录
     * @param code
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/getPhoneLogin")
    @ApiOperation(value = "解析用户手机号登录", notes = "解析用户手机号登录")
    @ApiImplicitParam(name = "code", value = "手机号授权码")
    public ResponseEntity<?> getPhoneLogin(@RequestParam(value = "code") String code,@RequestParam(value = "openId",required = false) String openId) throws WxErrorException {
        String accessToken = wxConfig.getWxMaService().getAccessToken();
        String phone = getWxUserPhone(code, accessToken);

        //有用户就登录,没有就注册
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, phone));
        if (ObjectUtils.isEmpty(user)) {
            // 新建用户
            user = new User();
            user.setModifyTime(new Date());
            user.setUserRegtime(new Date());
            user.setUserRegip(IPHelper.getIpAddr());
            user.setStatus(1);
            user.setUserMobile(phone);
            user.setUserName(phone);
            user.setNickName(phone);
            appConnectService.registerOrBindUser(user, null);
        }

        // 登录
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setIsAdmin(0);
        userInfoInTokenBO.setEnabled(true);

        //第三方用户
        if (StringUtils.isNotEmpty(openId)){
            Integer socialType = SocialType.MA.value();
            AppConnect appConnect = appConnectService.getByBizUserId(openId, socialType);
            if (ObjectUtils.isEmpty(appConnect)) {
                String tempUid = IdUtil.simpleUUID();
                appConnect = new AppConnect();
                appConnect.setAppId(socialType);
                appConnect.setTempUid(tempUid);
                appConnect.setBizUserId(openId);
                appConnect.setUserId(user.getUserId());
//                appConnect.setBizUnionid(session.getUnionid());
//                appConnect.setBizTempSession(session.getSessionKey());
                appConnectService.save(appConnect);
            }
            userInfoInTokenBO.setBizUserId(appConnect.getBizUserId());
        }

        return ResponseEntity.ok(tokenStore.storeAndGetVo(userInfoInTokenBO));

    }


    /**
     *	解析用户手机号
     */
    private String getWxUserPhone(String mobileCode,String accessToken) {
        // 请求解析手机号
        StringBuilder getPhoneNumberUrl = new StringBuilder(WX_GET_PHONE_NUMBER_URL);
        getPhoneNumberUrl.append("?access_token=").append(accessToken);
        Map<String, String> phoneNumberParam = new HashMap<>();
        phoneNumberParam.put("code", mobileCode);
        ResponseEntity<HashMap> hashMapResponseEntity = restTemplate.postForEntity(getPhoneNumberUrl.toString(), phoneNumberParam, HashMap.class);
        if (hashMapResponseEntity.getStatusCodeValue() != 200 || Objects.isNull(hashMapResponseEntity.getBody()) || (Integer) hashMapResponseEntity.getBody().get("errcode")!=0) {
            throw new AppBindException("解析手机接口调用异常~");
        }

        LinkedHashMap phoneInfo = (LinkedHashMap)hashMapResponseEntity.getBody().get("phone_info");
        String purePhoneNumber = (String)phoneInfo.get("purePhoneNumber");
        return purePhoneNumber;
    }





    /**
     *
     *  解析用户手机号登录
     * @param code
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/getOpenId")
    @ApiOperation(value = "获取用户openId", notes = "获取用户openId")
    @ApiImplicitParam(name = "code", value = "授权码")
    public ResponseEntity<?> getOpeId(@RequestParam(value = "code") String code) throws WxErrorException {
        WxMaService wxMaService = wxConfig.getWxMaService();
        WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
        return ResponseEntity.ok(session.getOpenid());
    }

}
