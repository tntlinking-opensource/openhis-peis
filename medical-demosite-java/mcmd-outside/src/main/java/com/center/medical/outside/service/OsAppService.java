package com.center.medical.outside.service;

import java.util.Map;

/**
 * @author: 路飞船长
 * @date: 2023/9/20 13:46
 * @description: 小程序端接口业务
 */
public interface OsAppService {


    /**
     * 更新预约记录的状态
     *
     * @param params 请求参数
     * @return
     */
    Boolean updateRVStatus(Map<String, Object> params);
}
