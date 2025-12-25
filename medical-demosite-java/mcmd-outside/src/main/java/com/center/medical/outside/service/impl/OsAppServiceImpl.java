package com.center.medical.outside.service.impl;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.constant.SourcesConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.outside.service.OsAppService;
import com.center.medical.outside.config.OsAppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: 路飞船长
 * @date: 2023/9/20 13:46
 * @description: 小程序端接口业务
 */
@Slf4j
@Service("osAppService")
@RequiredArgsConstructor
public class OsAppServiceImpl implements OsAppService {

    private final OsAppConfig osAppConfig;

    /**
     * 更新预约记录的状态
     *
     * @param params 请求参数
     * @return
     */
    @Override
    public Boolean updateRVStatus(Map<String, Object> params) {
        log.info("沃德小程序更新预约记录的状态:{}", params);
        String data = JSONUtil.toJsonStr(params);
        //加密
        R<String> encrypt = osAppConfig.encrypt(data);
        if (R.isSuccess(encrypt)) {
            String paramValue = encrypt.getData();
            //发送请求
            R result = osAppConfig.send(SourcesConstants.APP_RV_UPDATESTATUS_PATH, 2, paramValue);
            return R.isSuccess(result);
        }
        return Boolean.FALSE;
    }
}

