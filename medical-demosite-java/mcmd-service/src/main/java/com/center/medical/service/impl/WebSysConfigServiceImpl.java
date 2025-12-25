package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysConfig;
import com.center.medical.dao.WebSysConfigMapper;
import com.center.medical.service.WebSysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 参数配置表(SysConfig)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-10-17 09:23:41
 */
@Service("webSysConfigService")
@RequiredArgsConstructor
public class WebSysConfigServiceImpl extends ServiceImpl<WebSysConfigMapper, SysConfig> implements WebSysConfigService {

    private final WebSysConfigMapper sysConfigMapper;

}

