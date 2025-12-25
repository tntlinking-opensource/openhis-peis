package com.center.medical.outside.service;

import com.center.medical.common.core.domain.R;
import com.center.medical.outside.bean.Vo.AuthTokenVO;
import com.center.medical.outside.bean.param.OutsideLoginParam;

/**
 * @author: 路飞船长
 * @date: 2025/6/16 10:21
 * @description:
 */
public interface OutsideLoginService {

    R<AuthTokenVO> login(OutsideLoginParam param);
}
