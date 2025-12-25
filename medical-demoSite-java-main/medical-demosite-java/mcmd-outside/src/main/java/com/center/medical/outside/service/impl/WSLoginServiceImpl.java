package com.center.medical.outside.service.impl;

import com.center.medical.common.core.domain.R;
import com.center.medical.outside.bean.Vo.AuthTokenVO;
import com.center.medical.outside.bean.dto.WSLoginDto;
import com.center.medical.outside.bean.model.WSLoginHead;
import com.center.medical.outside.bean.model.WSLoginMessage;
import com.center.medical.outside.bean.param.OutsideLoginParam;
import com.center.medical.outside.bean.param.WSLoginParam;
import com.center.medical.outside.service.WSLoginService;
import com.center.medical.outside.service.OutsideLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * 博英心电信息管理系统对接
 * @author xhp
 * @since 2025-05-23 10:04
 */
@Service
@Slf4j
@WebService(name="wsLoginService"
        ,targetNamespace = "http://service.outside.medical.world.com"
        ,endpointInterface = "com.center.medical.outside.service.WSLoginService")
public class WSLoginServiceImpl implements WSLoginService {
//    @Resource(name="boyingBusinessService")
//    private BoyingBusinessService boyingBusinessService;
    @Autowired
    private OutsideLoginService outsideLoginService;

    @Override
    public WSLoginDto outsideLogin(WSLoginParam param) {
        WSLoginDto dto = new WSLoginDto();
        OutsideLoginParam loginParam = new OutsideLoginParam();
        loginParam.setUserName(param.getUserName());
        loginParam.setPassword(param.getPassword());
        loginParam.setSourceId(param.getSourceId());
        R<AuthTokenVO> result = outsideLoginService.login(loginParam);
        WSLoginMessage message = new WSLoginMessage();
        if (R.isSuccess(result)){
            message.setAccessToken(result.getData().getAccessToken());
            message.setExpiresIn(result.getData().getExpiresIn());
            message.setSuccess(true);
        }else {
            message.setSuccess(false);
            message.setAccessToken(result.getMsg());
        }
        WSLoginHead head = new WSLoginHead();
        head.setServiceName("OutsideLogin");
        head.setParam(null);
        head.setMessCount("");
        head.setSuccess(true);
        dto.setHead(head);
        dto.setMessage(message);
        return dto;
    }
}
